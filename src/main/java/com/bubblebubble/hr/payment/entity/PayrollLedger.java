package com.bubblebubble.hr.payment.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_PAYROLL_LEDGER")
@NoArgsConstructor
@Getter
@DynamicInsert
public class PayrollLedger {
    @Id
    @Column(name = "PAYROLL_NO")
    private int no;
    @Column(name = "PAYROLL_NAME")
    private String name;
    @Column(name = "PAYMENT_SCHEDULED_DATE")
    private LocalDateTime paymentScheduledDate;
    @Column(name = "SALARY_BASE_START_DATE")
    private LocalDateTime salaryBaseStartDate;
    @Column(name = "SALARY_BASE_END_DATE")
    private LocalDateTime salaryBaseEndDate;
    @Column(name = "TOTAL_SALARY_AMOUNT")
    private int totalSalaryAmount;
    @Column(name = "TOTAL_AMOUNT_TAX")
    private int totalAmountTax;
    @Column(name = "NET_INCOME")
    private int netIncome;
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    @Column(name = "IS_CLOSED")
    private String isClosed;


    @Builder
    public PayrollLedger(int no, String name, LocalDateTime paymentScheduledDate, LocalDateTime salaryBaseStartDate,
            LocalDateTime salaryBaseEndDate, int totalSalaryAmount, int totalAmountTax, int netIncome,
            LocalDateTime createDate, String isClosed) {
        this.no = no;
        this.name = name;
        this.paymentScheduledDate = paymentScheduledDate;
        this.salaryBaseStartDate = salaryBaseStartDate;
        this.salaryBaseEndDate = salaryBaseEndDate;
        this.totalSalaryAmount = totalSalaryAmount;
        this.totalAmountTax = totalAmountTax;
        this.netIncome = netIncome;
        this.createDate = createDate;
        this.isClosed = isClosed;
    }

    
}
