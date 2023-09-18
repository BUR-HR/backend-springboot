package com.bubblebubble.hr.apis.login.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bubblebubble.hr.apis.login.dto.EmployeeAndJobDTO;
import com.bubblebubble.hr.apis.login.dto.EmployeeDTO;
import com.bubblebubble.hr.apis.login.service.MemberService;
import com.bubblebubble.hr.common.ResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;

    }

    @Operation(summary = "회원 등록", description = "회원 등록을 진행합니다.", tags = {"MemberController"})
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("[RegisterController] registerEmployee start ================= ");
        try {
            EmployeeDTO registeredEmployeeDTO = memberService.registerEmployee(employeeDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원가입 성공", registeredEmployeeDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "직원 등록에 실패하였습니다.", null));
        }
    }

    //직원전체조회
    @GetMapping("/employee/all")
    public ResponseEntity<ResponseDTO> getAllEmployees(@RequestParam(required = false) EmployeeDTO employeeDTO) {
        log.info("[MemberController] getAllEmployees start ================= ");

        try {
            List<EmployeeDTO> employees = memberService.getAllEmployees();
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 직원 목록 조회 성공", employees));
        } catch (Exception e) {
            log.error("An error occurred during employee lookup: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "전체 직원 목록 조회 중 에러가 발생했습니다.", null));
        }
    }

    @GetMapping("employee/empAll")
    public ResponseEntity<ResponseDTO> getEmpAllList(){

        log.info("[MemberController] getEmpAllList start ========");

        Map<String, List<EmployeeAndJobDTO>> map = memberService.getEmpAllList();
        System.out.println("map = " + map);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"전체 사원 조회",map));
    }

}




