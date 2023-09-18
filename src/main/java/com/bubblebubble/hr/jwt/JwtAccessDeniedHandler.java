package com.bubblebubble.hr.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증은 되었지만, 요청에 대한 권한이 없을 때 호출되는 핸들러를 커스터마이징함
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    // 권한이 없는 요청이 발생할때 호출하는 메서드
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response
            , AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        // 요청에 대한 권한이 없을때, 클라이언트에게 401 응답 보냄
    }
}