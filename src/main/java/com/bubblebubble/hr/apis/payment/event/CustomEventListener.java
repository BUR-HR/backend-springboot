package com.bubblebubble.hr.apis.payment.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.bubblebubble.hr.apis.file.service.MailService;
import com.bubblebubble.hr.apis.payment.service.SalaryService;

@Component
public class CustomEventListener {
    private final SalaryService salaryService;
    private final MailService mailService;

    public CustomEventListener(SalaryService salaryService, MailService mailService) {
        this.salaryService = salaryService;
        this.mailService = mailService;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void insertDefaultSalary(RegisterEmployeeEvent event) {
        salaryService.insertDefaultSalary(event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendEmail(SendEmailEvent sendEmailEvent) {
        String email = sendEmailEvent.getEmail();

        mailService.sendEmail(email, sendEmailEvent.getEmpNo(), sendEmailEvent.getTemporaryPassword());
    }

}