package com.bubblebubble.hr.controller;

import com.bubblebubble.hr.Service.FileService;
import com.bubblebubble.hr.Service.MailService;
import com.bubblebubble.hr.employee.service.EmpcardService;
import com.bubblebubble.hr.login.common.ResponseDTO;
import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.member.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@Slf4j
public class  FileController {

    private final FileService fileService;

    private EmpcardService empcardService;

    @Autowired
    private MailService mailService;


    public FileController(FileService fileService, EmpcardService empcardService) {
        this.fileService = fileService;
        this.empcardService = empcardService;
    }

    @PostMapping("/fileimgs")
    public ResponseEntity<ResponseDTO> insertFile(@ModelAttribute EmployeeDTO employeeDTO, MultipartFile fileImgs) {
        String resultMessage = fileService.insertFile(employeeDTO, fileImgs);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, resultMessage, null);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@ModelAttribute EmployeeDTO employee, MultipartFile fileImgs) {
        try {
            System.out.println("employee = " + employee);
            String temporaryPassword = EmpcardService.generateTemporaryPassword();
            System.out.println("temporaryPassword = " + temporaryPassword);
            Employee registeredEmployee = empcardService.registerEmployee(employee, temporaryPassword);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("empNo", registeredEmployee.getEmpNo());
            responseMap.put("tempPass", temporaryPassword);

            String email = employee.getEmployeeEmail();


            // 이메일 보내기
            mailService.sendEmail(email, String.valueOf(registeredEmployee.getEmpNo()), temporaryPassword);




            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원가입 성공", responseMap));
        } catch (Exception e) {
            System.out.println("An error occurred during employee registration: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for detailed information
            return ResponseEntity.status(500).body("직원 등록에 실패하였습니다.");
        }
    }


}
