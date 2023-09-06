package com.bubblebubble.hr.payment.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_SEVERANCE_LEDGER")
public class SeveranceLedger {
    @Id
    @Column(name = "SEVERANCE_NO")
    private int severanceNo;

    @Column(name = "SEVERANCE_NAME")
    private String severanceName;

    @Column(name = "PAYMENT_SCHEDULED_DATE")
    private LocalDateTime paymentScheduleDate;

    @Column(name = "SALARY_BASE_START_DATE")
    private LocalDateTime salaryBaseStartDate;

    @Column(name = "SALARY_BASE_END_DATE")
    private LocalDateTime salaryBaseEndDate;

    @Column(name = "TOTAL_SALARY_AMOUNT")
    private long totalSalaryAmount;

    @Column(name = "TOTAL_AMOUNT_TAX")
    private long totalAmountTax;

    @Column(name = "NET_INCOME")
    private long netIncome;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "IS_CLOSED")
    private String isClosed;
}
