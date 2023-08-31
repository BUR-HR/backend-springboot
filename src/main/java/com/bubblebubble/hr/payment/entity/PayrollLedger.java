package com.bubblebubble.hr.payment.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PAYROLL_LEDGER")
public class PayrollLedger {
    @EmbeddedId
    private PayrollPK payrollPK;
}
