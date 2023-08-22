package com.bubblebubble.hr.login.controller;


import com.bubblebubble.hr.login.common.ResponseDTO;
import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    /* @RequestBody를 통해 RequestBody로 넘어온 Json문자열을 파싱해서 MemberDTO 속성으로
     *  매핑해 객체로 받아낸다(회원 아이디, 비밀번호)
     * */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody EmployeeDTO memberDTO){


        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK, "로그인 성공", authService.login(memberDTO)));
    }

}