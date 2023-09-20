package com.bubblebubble.hr.jwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.bubblebubble.hr.apis.login.dto.TokenDTO;
import com.bubblebubble.hr.apis.login.member.entity.Employee;
import com.bubblebubble.hr.apis.login.member.entity.EmployeeRole;
import com.bubblebubble.hr.exception.TokenException;
import com.bubblebubble.hr.jwt.entity.RefreshToken;
import com.bubblebubble.hr.jwt.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String BEARER_TYPE = "Bearer";

    // AccessToken 유효시간 10분
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 10 * 60;

    // RefreshToken 유효시간 7일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    private final Key key;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsService userDetailsService;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
            UserDetailsService userDetailsService,
            RefreshTokenRepository refreshTokenRepository) {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // 토큰 생성 메소드
    public TokenDTO generateTokenDTO(Employee employee, HttpServletResponse response) {
        log.info("[TokenProvider] generateTokenDTO Start ===================");
        List<String> roles = new ArrayList<>(); //멤버의 권한을 추출
        for (EmployeeRole employeeRole : employee.getEmployeeRole()) {
            roles.add(employeeRole.getAuthority().getAuthName());
        }

        log.info("[TokenProvider] authorites {} ", roles);

        // 회원 아이디를 "sub"라는 클레임으로 토큰에 추가
        Claims claims = Jwts.claims().setSubject(String.valueOf(employee.getEmpNo()));

        // 회원의 권한을 "auth"라는 클레임으로 토큰에 추가
        claims.put(AUTHORITIES_KEY, roles);

        String accessToken = createAccessToken(claims);
        String refreshToken = createRefreshToken(claims);

        refreshTokenRepository.save(new RefreshToken(refreshToken, employee.getEmpNo()), REFRESH_TOKEN_EXPIRE_TIME);

        ResponseCookie cookie = getRefreshTokenCookie(refreshToken);
        response.setHeader("Set-Cookie", cookie.toString());
        log.info("[TokenProvider] generateTokenDTO End ====================== ");

        return new TokenDTO(BEARER_TYPE, employee.getEmpName(), accessToken, ACCESS_TOKEN_EXPIRE_TIME);
    }

    // 1-1. access Token 생성
    public String createAccessToken(Claims claims) {

        return this.createToken(claims, ACCESS_TOKEN_EXPIRE_TIME);
    }

    // 1-2. refresh Token 생성
    public String createRefreshToken(Claims claims) {

        return this.createToken(claims, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String createToken(Claims claims, long tokenValid) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValid))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 2. 토큰의 등록된 클레임의 subject에서 해당 회원의 아이디 추출
    public String getEmpNo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 3. AccessToken으로 인증 객체 추출
    public Authentication getAuthentication(String token) {

        log.info("[TokenProvider] getAuthentication Start ==================== ");
        //토큰에서 claim들을 추출(토큰 복호화)
        Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmpNo(token));
        log.info("[TokenProvider] ======================= {}", userDetails.getAuthorities());

        log.info("[TokenProvider] getAuthentication End ======================= ");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 4. 토큰 유효성 검사
    public boolean validateToken(String accessToken) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new TokenException("만료된 JWT 토큰입니다.");
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new TokenException("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
            throw new TokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new TokenException("JWT 토큰이 잘못되었습니다.");
        }
    }

    // 5. AccessToken에서 클레임 추출하는 메소드
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private ResponseCookie getRefreshTokenCookie(String refreshToken) {

        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(REFRESH_TOKEN_EXPIRE_TIME / 1000)
                .path("/")
                .secure(true)
                .sameSite(SameSite.NONE.name())
                .httpOnly(true)
                .build();
    }

    public TokenDTO reissuanceAccessToken(Employee employee) {
        log.info("[TokenProvider] reissuanceAccessToken Start ===================");
        List<String> roles = new ArrayList<>(); //멤버의 권한을 추출
        for (EmployeeRole employeeRole : employee.getEmployeeRole()) {
            roles.add(employeeRole.getAuthority().getAuthName());
        }

        log.info("[TokenProvider] authorites {} ", roles);

        // 회원 아이디를 "sub"라는 클레임으로 토큰에 추가
        Claims claims = Jwts.claims().setSubject(String.valueOf(employee.getEmpNo()));

        // 회원의 권한을 "auth"라는 클레임으로 토큰에 추가
        claims.put(AUTHORITIES_KEY, roles);

        String accessToken = createAccessToken(claims);

        log.info("[TokenProvider] reissuanceAccessToken End ====================== ");

        return new TokenDTO(BEARER_TYPE, employee.getEmpName(), accessToken, ACCESS_TOKEN_EXPIRE_TIME);
    }
}
