package com.bubblebubble.hr.login.jwt;

//토큰 생성, 토큰 인증(Authentication 객체 반환), 토큰 유효성 검사

import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.dto.TokenDTO;
import com.bubblebubble.hr.login.exception.TokenException;
import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.login.member.entity.EmployeeRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String BEARER_TYPE = "Bearer";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private final Key key;

    private final UserDetailsService userDetailsService;
    public TokenProvider(@Value("${jwt.secret}") String secretKey, UserDetailsService userDetailsService){

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
    }

    // 토큰 생성 메소드
    public TokenDTO generateTokenDTO(Employee employee){
        log.info("[TokenProvider] generateTokenDTO Start ===================");
        List<String> roles = new ArrayList<>(); //멤버의 권한을 추출
        for(EmployeeRole employeeRole : employee.getEmployeeRole()) {
            roles.add(employeeRole.getAuthority().getAuthName());
        }

        log.info("[TokenProvider] authorites {} ", roles);

       // 회원 아이디를 "sub"라는 클레임으로 토큰에 추가
        Claims claims = Jwts.claims().setSubject(String.valueOf(employee.getEmpNo()));


        // 회원의 권한을 "auth"라는 클레임으로 토큰에 추가
        claims.put(AUTHORITIES_KEY,roles);

        long now = System.currentTimeMillis(); // 현재 시간을 밀리세컨드 단위로 가져옴

        Date accessTokenExpriesIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(accessTokenExpriesIn) // 토큰의 만료기간을 data형으로 토큰에 추가(exp라는 클레임으로 long형으로 토큰에 추가)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        log.info("[TokenProvider] generateTokenDTO End ====================== ");

        return new TokenDTO(BEARER_TYPE, employee.getEmpName(), accessToken, accessTokenExpriesIn.getTime());
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
        public Authentication getAuthentication(String token){

            log.info("[TokenProvider] getAuthentication Start ==================== ");
            //토큰에서 claim들을 추출(토큰 복호화)
            Claims claims = parseClaims(token);

            if(claims.get(AUTHORITIES_KEY) == null) {
                throw new RuntimeException("권한 정보가 없는 토큰입니다.");
            }

            String empNo = claims.getSubject();

            UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmpNo(token));
            log.info("[TokenProvider] ======================= {}", userDetails.getAuthorities());

            log.info("[TokenProvider] getAuthentication End ======================= ");
            return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
        }

        // 4. 토큰 유효성 검사
        public boolean validateToken(String token){

            try {
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return true;
            }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
                log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
                throw new TokenException("잘못된 JWT 서명입니다.");
            } catch (ExpiredJwtException e){
                log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
                throw new TokenException("만료된 JWT 토큰입니다.");
            } catch (UnsupportedJwtException e){
                log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
                throw new TokenException("지원되지 않는 JWT 토큰입니다.");
            } catch (IllegalArgumentException e){
                log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
                throw new TokenException("JWT 토큰이 잘못되었습니다.");
            }
        }



    // 5. AccessToken에서 클레임 추출하는 메소드
    private Claims parseClaims(String token){
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
