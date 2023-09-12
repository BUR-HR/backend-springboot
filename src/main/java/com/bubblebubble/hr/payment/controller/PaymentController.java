package com.bubblebubble.hr.payment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bubblebubble.hr.login.exception.NotFoundEmployeeSalaryException;
import com.bubblebubble.hr.payment.dto.PayrollLedgerInsertRequestDTO;
import com.bubblebubble.hr.payment.dto.PayrollLedgerResponseDTO;
import com.bubblebubble.hr.payment.dto.SeveranceLedgerInsertRequestDTO;
import com.bubblebubble.hr.payment.dto.SeveranceLedgerResponseDTO;
import com.bubblebubble.hr.payment.service.NotFoundPayrollLedgerException;
import com.bubblebubble.hr.payment.service.NotFoundSeveranceLedgerException;
import com.bubblebubble.hr.payment.service.PaymentService;

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
    public ResponseEntity<?> getPayrollList(String name) {
        log.info("[PaymentController] getPayrollList start");
        log.info("name {}", name);

        List<PayrollLedgerResponseDTO> response = paymentService.getPayrollList(name);
        log.info("[PaymentController] getPayrollList end");
        return ResponseEntity.ok().body(response);
    };

    @PostMapping("payroll")
    public ResponseEntity<?> insertPayroll(@ModelAttribute PayrollLedgerInsertRequestDTO payrollRequest)
            throws NotFoundEmployeeSalaryException {
        log.info("[PaymentController] insertPayroll start");
        log.info("payrollRequest {}", payrollRequest);
        List<PayrollLedgerResponseDTO> response = paymentService.insertPayroll(payrollRequest);

        log.info("response {}", response);
        log.info("[PaymentController] insertPayroll end");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("payroll")
    public ResponseEntity<?> deletePayroll(@RequestBody Map<String, Integer> request)
            throws NotFoundEmployeeSalaryException, NotFoundPayrollLedgerException, IllegalAccessException {
        log.info("[PaymentController] deletePayroll start");
        log.info("no {}", request.get("no"));
        
        List<PayrollLedgerResponseDTO> response = paymentService.deletePayroll(request.get("no"));
        
        log.info("[PaymentController] deletePayroll end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PutMapping("payroll/close")
    public ResponseEntity<?> closePayrollLedger(@RequestBody Map<String, Integer> request) throws NotFoundPayrollLedgerException {
        
        log.info("[PaymentController] closePayrollLedger start");
        List<PayrollLedgerResponseDTO> response = paymentService.closePayrollLedger(request.get("no"));
        
        log.info("[PaymentController] closePayrollLedger end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("severance")
    public ResponseEntity<?> getSeveranceList(@RequestBody String name) {

        log.info("[PaymentController] getPayrollList start");
        log.info("name {}", name);

        List<SeveranceLedgerResponseDTO> response = paymentService.getSeveranceList(name);
        log.info("[PaymentController] getPayrollList end");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("severance")
    public ResponseEntity<?> insertSeverance(@ModelAttribute SeveranceLedgerInsertRequestDTO severanceRequest) {
        log.info("[PaymentController] insertSeverance start");
        log.info("payrollRequest {}", severanceRequest);
        paymentService.insertSeverance(severanceRequest);
        log.info("[PaymentController] insertSeverance end");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("severance")
    public ResponseEntity<?> deleteSeverance(@RequestBody Map<String, Integer> request)
            throws NotFoundEmployeeSalaryException, NotFoundPayrollLedgerException, IllegalAccessException, NotFoundSeveranceLedgerException {
        log.info("[PaymentController] deletePayroll start");
        log.info("no {}", request.get("no"));
        
        List<SeveranceLedgerResponseDTO> response = paymentService.deleteSeverance(request.get("no"));
        
        log.info("[PaymentController] deletePayroll end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("severance/close")
    public ResponseEntity<?> closeSeveranceLedger(@RequestBody Map<String, Integer> request) throws NotFoundPayrollLedgerException, NotFoundSeveranceLedgerException {
        
        log.info("[PaymentController] closePayrollLedger start");
        List<SeveranceLedgerResponseDTO> response = paymentService.closeSeveranceLedger(request.get("no"));
        
        log.info("[PaymentController] closePayrollLedger end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
