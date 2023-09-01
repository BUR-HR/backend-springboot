package com.bubblebubble.hr.login.controller;

import com.bubblebubble.hr.employee.service.EmpcardService;
import com.bubblebubble.hr.login.common.ResponseDTO;
import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.login.repository.MemberRepository;
import com.bubblebubble.hr.login.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;

    }

    @Operation(summary = "직원 조회 요청", description = "직원 한명이 조회됩니다.", tags = {"MemberController"})
    @GetMapping("/employee/{empNo}")
    public ResponseEntity<ResponseDTO> selectMyMemberInfo(@PathVariable String empNo) {

        log.info("[MemberController] selectMyMemberInfo start ================= ");
        log.info("[MemberController] selectMyMemberInfo {} ========= ", empNo);

        try {
            EmployeeDTO employee = memberService.selectMyInfo(empNo);
            if (employee != null) {
                return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", employee));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(HttpStatus.NOT_FOUND, "직원을 찾을 수 없습니다.", null));
            }
        } catch (Exception e) {
            log.error("An error occurred during employee lookup: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "조회 중 에러가 발생했습니다.", null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO registeredEmployeeDTO = memberService.registerEmployee(employeeDTO);

            // 등록된 직원의 사원번호를 응답에 포함시킬 수도 있음
//            EmployeeDTO registeredEmployeeDTO = memberService.selectMyInfo(String.valueOf(registeredEmployee.getEmpNo()));
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원가입 성공", registeredEmployeeDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "직원 등록에 실패하였습니다.", null));
        }
    }



}
