package com.bubblebubble.hr.apis.login.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubblebubble.hr.apis.login.dto.EmployeeDTO;
import com.bubblebubble.hr.apis.login.service.AuthService;
import com.bubblebubble.hr.common.ResponseDTO;

import lombok.extern.slf4j.Slf4j;

// 로그인 요청 처리
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /* @RequestBody를 통해 RequestBody로 넘어온 Json문자열을 파싱해서 MemberDTO 속성으로
     *  매핑해 객체로 받아낸다(회원 아이디, 비밀번호)
     * */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody EmployeeDTO memberDTO, HttpServletResponse response) {
        log.info("[login] start =========");
        Object accessTokenDTO = authService.login(memberDTO, response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.OK, "로그인 성공", accessTokenDTO));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ResponseDTO> reissue(@AuthenticationPrincipal EmployeeDTO employeeDTO) {
        Object accessTokenDTO = authService.reissuanceAccessToken(employeeDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.OK, "AccessToken 재발급 성공", accessTokenDTO));
    }
}