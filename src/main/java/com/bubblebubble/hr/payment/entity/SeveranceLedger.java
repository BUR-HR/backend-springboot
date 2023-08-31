package com.bubblebubble.hr.payment.entity;

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
}
