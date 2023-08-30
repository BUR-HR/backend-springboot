package com.bubblebubble.hr.login.exception;


import com.bubblebubble.hr.attendance.controller.AttendanceInfoNotFoundException;
import com.bubblebubble.hr.login.exception.dto.ApiExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/* 예외 처리용 어드바이스 추가 */
/* 요청 처리에 있어서 Controller 단에서 발생하는 각 예외 상황별 예외 처리용
 * (@ControllerAdvice + @ResponseBody)
 * */
@RestControllerAdvice
public class ApiExceptionAdvice {

    /* AuthService에서 비밀번호 불일치 시 발생하는 예외 처리 */
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(LoginFailedException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    /* 이메일이 중복됐을 때 발생하는 예외 상황 처리 */
    @ExceptionHandler(DuplicatedMemberEmailException.class)
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(DuplicatedMemberEmailException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(AttendanceInfoNotFoundException.class)
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(AttendanceInfoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(new ApiExceptionDTO(HttpStatus.NO_CONTENT, e.getMessage()));
    }
}
