package com.bubblebubble.hr.payment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubblebubble.hr.payment.dto.PayrollLedgerRequestDTO;
import com.bubblebubble.hr.payment.dto.PayrollLedgerResponseDTO;
import com.bubblebubble.hr.payment.service.PaymentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/pay")
@PreAuthorize("hasRole('ROLE_PAYROLL')")
@Slf4j
public class PaymentController {
    
    private final PaymentService paymentService;
    
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("payroll")
    public ResponseEntity<?> getPayrollList(@RequestBody String name) {
        log.info("[PaymentController] getPayrollList start");
        log.info("name {}", name);

        List<PayrollLedgerResponseDTO> response = paymentService.getPayrollList(name);
        log.info("[PaymentController] getPayrollList end");
        return ResponseEntity.ok().body(response);
    };

    @PostMapping("payroll")
    public ResponseEntity<?> insertPayroll(@ModelAttribute PayrollLedgerRequestDTO payrollRequest) {
        log.info("[PaymentController] insertPayroll start");
        log.info("payrollRequest {}", payrollRequest);
        log.info("[PaymentController] insertPayroll end");
        return null;
    }

    @GetMapping("severance")
    public ResponseEntity<?> getSeveranceList() {

        return ResponseEntity.ok().build();
    }


}
