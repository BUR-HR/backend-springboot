package com.bubblebubble.hr.payment.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeveranceLedgerRequestDTO {
    Long paymentNo;
    List<Long> empNo;
    Long payrollNo;
    String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date end_date;
    String payment_type;
}
