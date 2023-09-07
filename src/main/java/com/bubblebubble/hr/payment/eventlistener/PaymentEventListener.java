package com.bubblebubble.hr.payment.eventlistener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.bubblebubble.hr.payment.dto.RegisterEmployeeEvent;
import com.bubblebubble.hr.payment.service.SalaryService;

@Component
public class PaymentEventListener {
    private final SalaryService salaryService;
    
    public PaymentEventListener(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void insertDefaultSalary(RegisterEmployeeEvent event) {
        salaryService.insertDefaultSalary(event);
    }
}