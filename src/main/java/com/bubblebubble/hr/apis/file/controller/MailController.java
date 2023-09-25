package com.bubblebubble.hr.apis.file.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubblebubble.hr.apis.file.service.MailService;
import com.bubblebubble.hr.common.ResponseDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/mail")
@Slf4j
public class MailController {
    private final MailService mailService;
    
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_PAYROLL')")
    public ResponseEntity<?> sendPaymentEmail(int no) throws MessagingException, IOException {
        mailService.sendPaymentEmail(no);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 전송 성공", null));
    }

    @PostMapping("/payment")
    public String sendPaymentContent(String secret, String rrn) {

        log.info("log {} {}", secret, rrn);
        return null;
    }
}
