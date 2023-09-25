package com.bubblebubble.hr.apis.file.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bubblebubble.hr.apis.employee.service.EmpcardService;
import com.bubblebubble.hr.apis.file.service.FileService;
import com.bubblebubble.hr.apis.login.dto.EmployeeDTO;
import com.bubblebubble.hr.apis.login.member.entity.Employee;
import com.bubblebubble.hr.common.ResponseDTO;

@RestController
@RequestMapping("/api/file")
public class  FileController {

    private final FileService fileService;

    private final EmpcardService empcardService;

    private final PasswordEncoder passwordEncoder;


    public FileController(FileService fileService, EmpcardService empcardService, PasswordEncoder passwordEncoder) {
        this.fileService = fileService;
        this.empcardService = empcardService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/fileimgs")
    public ResponseEntity<ResponseDTO> insertFile(@ModelAttribute EmployeeDTO employeeDTO, MultipartFile fileImgs) {
        String resultMessage = fileService.insertFile(employeeDTO, fileImgs);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, resultMessage, null);

        return ResponseEntity.ok().body(responseDTO);
    }


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

            // 이메일
            Employee registeredEmployee = empcardService.registerEmployee(employeeDTO, temporaryPassword);

            if (registeredEmployee == null) {
                throw new Exception();
            }
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원가입 성공", null));

        } catch (Exception e) {
            System.out.println("An error occurred during employee registration: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for detailed information
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 등록에 실패하였습니다.");
        }
    }
    
}

