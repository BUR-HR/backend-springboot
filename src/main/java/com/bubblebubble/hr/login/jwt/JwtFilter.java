package com.bubblebubble.hr.login.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bubblebubble.hr.login.exception.TokenException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer";

    private final TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = resolveToken(request); // accessToken

        try {

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (TokenException e) {
            /*
            * TokenProvider에서 토큰 유효성 검사용 메소드 정의 시 사용
            * 유효성 검사 메소드는 JwtFilter에서 토큰 유효성 검사시 발생하는 예외 상황 처리
            * */
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 예: 401 Unauthorized
            response.getWriter().write("Token validation failed"); // 예외 메시지를 응답 본문에 포함할 수도 있습니다.
        }
    }

    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {

            return bearerToken.substring(7);
        }
        return null;

    }

}
