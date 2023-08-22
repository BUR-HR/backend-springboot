package com.bubblebubble.hr.login.controller;

import com.bubblebubble.hr.login.common.ResponseDTO;
import com.bubblebubble.hr.login.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) { this.memberService = memberService;}

    @Operation(summary = "직원 조회 요청", description = "직원 한명이 조회됩니다.", tags= { "MemberController" })
    @GetMapping("/employee/{empNo}")
    public ResponseEntity<ResponseDTO> selectMyMemberInfo(@PathVariable String empNo){

        log.info("[MemberController] selectMyMemberInfo start ================= ");
        log.info("[MemberController] selectMyMemberInfo {} ========= ", empNo);

        log.info("[MemberController] selectMyMemberInfo End ================= ");
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK, "조회 성공", memberService.selectMyInfo(empNo)));
    }
}
