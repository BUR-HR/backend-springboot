package com.bubblebubble.hr.employee.controller;

import com.bubblebubble.hr.employee.service.EmpcardService;
import com.bubblebubble.hr.login.common.ResponseDTO;
import com.bubblebubble.hr.login.member.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/employees")
public class EmpcardController {

    @Autowired
    private EmpcardService empcardService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) {
        try {
            System.out.println("employee = " + employee);
            String temporaryPassword = EmpcardService.generateTemporaryPassword();

            Employee registeredEmployee = empcardService.registerEmployee(employee, temporaryPassword);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원가입 성공", temporaryPassword));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("직원 등록에 실패하였습니다.");
        }
    }
}