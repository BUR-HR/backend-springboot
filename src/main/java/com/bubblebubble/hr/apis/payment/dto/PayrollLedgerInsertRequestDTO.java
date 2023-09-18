package com.bubblebubble.hr.apis.payment.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayrollLedgerInsertRequestDTO {
    private List<Integer> empNo;
    private Integer payrollNo;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate salaryBaseStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate salaryBaseEndDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentScheduledDate;
    private String paymentType;
}
