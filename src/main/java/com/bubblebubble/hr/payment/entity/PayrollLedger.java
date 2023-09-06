package com.bubblebubble.hr.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PAYROLL_LEDGER")
public class PayrollLedger {
    @Id
    @Column(name = "PAYROLL_NO")
    private int payrollNo;
}
