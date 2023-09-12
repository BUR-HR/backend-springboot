package com.bubblebubble.hr.payment.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayrollLedgerResponseDTO {

    private int no;
    private String name;
    private LocalDate paymentScheduledDate;
    private LocalDate salaryBaseStartDate;
    private LocalDate salaryBaseEndDate;
    private int totalSalaryAmount;
    private int totalAmountTax;
    private int netIncome;
    private LocalDate createDate;
    private String isClosed;
    private int count;
}
