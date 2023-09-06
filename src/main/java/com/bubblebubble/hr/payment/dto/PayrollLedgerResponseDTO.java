package com.bubblebubble.hr.payment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayrollLedgerResponseDTO {

    private int no;
    private String name;
    private LocalDateTime paymentScheduledDate;
    private LocalDateTime salaryBaseStartDate;
    private LocalDateTime salaryBaseEndDate;
    private int totalSalaryAmount;
    private int totalAmountTax;
    private int netIncome;
    private LocalDateTime createDate;
    private String isClosed;
}
