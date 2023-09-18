package com.bubblebubble.hr.apis.payment.controller;

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

import com.bubblebubble.hr.apis.payment.dto.PayrollLedgerInsertRequestDTO;
import com.bubblebubble.hr.apis.payment.dto.PayrollLedgerResponseDTO;
import com.bubblebubble.hr.apis.payment.dto.SeveranceLedgerInsertRequestDTO;
import com.bubblebubble.hr.apis.payment.dto.SeveranceLedgerResponseDTO;
import com.bubblebubble.hr.apis.payment.service.PaymentService;
import com.bubblebubble.hr.exception.NotFoundEmployeeSalaryException;
import com.bubblebubble.hr.exception.NotFoundPayrollLedgerException;
import com.bubblebubble.hr.exception.NotFoundSeveranceLedgerException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/pay")
@PreAuthorize("hasRole('ROLE_PAYROLL')")
@Slf4j
@Tag(name = "Payment", description = "지급 API")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(method = "GET", summary = "급여대장 조회", description = "급여대장을 조회한다.")
    @GetMapping("payroll")
    public ResponseEntity<?> getPayrollList(@Parameter(description = "대장명칭", required = false) String name) {
        log.info("[PaymentController] getPayrollList start");
        log.info("name {}", name);

        List<PayrollLedgerResponseDTO> response = paymentService.getPayrollList(name);
        log.info("[PaymentController] getPayrollList end");
        return ResponseEntity.ok().body(response);
    };

    @Operation(method = "POST", summary = "급여대장 추가", description = "급여대장을 추가한다.")
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

    @Operation(summary = "급여대장 삭제", description = "급여대장을 삭제한다.")
    @DeleteMapping("payroll")
    public ResponseEntity<?> deletePayroll(@Parameter(name = "no", description = "급여대장 번호") @RequestBody Map<String, Integer> request)
            throws NotFoundEmployeeSalaryException, NotFoundPayrollLedgerException, IllegalAccessException {
        log.info("[PaymentController] deletePayroll start");
        log.info("no {}", request.get("no"));

        List<PayrollLedgerResponseDTO> response = paymentService.deletePayroll(request.get("no"));

        log.info("[PaymentController] deletePayroll end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "급여대장 마감", description = "급여대장을 마감한다.")
    @PutMapping("payroll/close")
    public ResponseEntity<?> closePayrollLedger(@RequestBody Map<String, Integer> request)
            throws NotFoundPayrollLedgerException {

        log.info("[PaymentController] closePayrollLedger start");
        List<PayrollLedgerResponseDTO> response = paymentService.closePayrollLedger(request.get("no"));

        log.info("[PaymentController] closePayrollLedger end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "퇴직금 대장 조회", description = "퇴직금대장을 조회한다.")
    @GetMapping("severance")
    public ResponseEntity<?> getSeveranceList(@RequestBody String name) {

        log.info("[PaymentController] getPayrollList start");
        log.info("name {}", name);

        List<SeveranceLedgerResponseDTO> response = paymentService.getSeveranceList(name);
        log.info("[PaymentController] getPayrollList end");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "퇴직금 대장 추가", description = "퇴직금대장을 추가한다.")
    @PostMapping("severance")
    public ResponseEntity<?> insertSeverance(@ModelAttribute SeveranceLedgerInsertRequestDTO severanceRequest) {
        log.info("[PaymentController] insertSeverance start");
        log.info("payrollRequest {}", severanceRequest);
        paymentService.insertSeverance(severanceRequest);
        log.info("[PaymentController] insertSeverance end");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "퇴직금 대장 삭제", description = "퇴직금대장을 삭제한다.")
    @DeleteMapping("severance")
    public ResponseEntity<?> deleteSeverance(@Parameter(name = "no", description = "퇴직금대장 번호") @RequestBody Map<String, Integer> request)
            throws NotFoundEmployeeSalaryException, NotFoundPayrollLedgerException, IllegalAccessException,
            NotFoundSeveranceLedgerException {
        log.info("[PaymentController] deletePayroll start");
        log.info("no {}", request.get("no"));

        List<SeveranceLedgerResponseDTO> response = paymentService.deleteSeverance(request.get("no"));

        log.info("[PaymentController] deletePayroll end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "퇴직금 대장 마감", description = "퇴직금대장을 마감한다.")
    @PutMapping("severance/close")
    public ResponseEntity<?> closeSeveranceLedger(@RequestBody Map<String, Integer> request)
            throws NotFoundPayrollLedgerException, NotFoundSeveranceLedgerException {

        log.info("[PaymentController] closePayrollLedger start");
        List<SeveranceLedgerResponseDTO> response = paymentService.closeSeveranceLedger(request.get("no"));

        log.info("[PaymentController] closePayrollLedger end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
