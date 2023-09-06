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
import org.springframework.security.crypto.password.PasswordEncoder;
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


    private MailService mailService;

    private PasswordEncoder passwordEncoder;


    public FileController(FileService fileService, EmpcardService empcardService,MailService mailService,PasswordEncoder passwordEncoder) {
        this.fileService = fileService;
        this.empcardService = empcardService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/fileimgs")
    public ResponseEntity<ResponseDTO> insertFile(@ModelAttribute EmployeeDTO employeeDTO, MultipartFile fileImgs) {
        String resultMessage = fileService.insertFile(employeeDTO, fileImgs);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, resultMessage, null);

        return ResponseEntity.ok().body(responseDTO);
    }

    // 1파일 앤드포인트 직원등록 앤드포인트로 합치니까 파일 들어오는데 비밀번호에 널값 들어감
    // 2지금같이 따로 빼면 파일은 안들어가는데 직원등록은 됨 ; 코드 비교해보고 확인해보기 (노션에있는 코드는 1번)
    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@ModelAttribute EmployeeDTO employeeDTO, @RequestParam("fileImgs") MultipartFile fileImgs) {
        try {
            System.out.println("employee = " + employeeDTO);
            String temporaryPassword = EmpcardService.generateTemporaryPassword();
            // 나중에 로그인 사용자에게 전달할 원본 비밀번호
            System.out.println("temporaryPassword = " + temporaryPassword);
            // 원본 비밀번호를 암호화해서 데이터베이스에 저장해야하기때문에 변환해서 전달

            employeeDTO.setEmployeePassword(passwordEncoder.encode(temporaryPassword));
            // 파일 업로드 처리
            String resultMessage = fileService.insertFile(employeeDTO, fileImgs);

            if (!resultMessage.equals("File uploaded successfully")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드에 실패하였습니다.");
            }

            Employee registeredEmployee = empcardService.registerEmployee(employeeDTO, temporaryPassword);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("empNo", registeredEmployee.getEmpNo());
            responseMap.put("tempPass", temporaryPassword);

            String email = employeeDTO.getEmployeeEmail();

            // 이메일 보내기
            mailService.sendEmail(email, String.valueOf(registeredEmployee.getEmpNo()), temporaryPassword);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원가입 성공", responseMap));

        } catch (Exception e) {
            System.out.println("An error occurred during employee registration: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for detailed information
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 등록에 실패하였습니다.");
        }
    }
}

