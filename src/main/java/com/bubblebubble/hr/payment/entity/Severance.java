package com.bubblebubble.hr.payment.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_SEVERANCE")
@Getter
@NoArgsConstructor
public class Severance {
    @EmbeddedId
    private SeverancePK id;

    @ManyToOne
    @JoinColumn(name = "SEVERANCE_NO", insertable = false, updatable = false)
    private SeveranceLedger severanceLedger;

    public Severance(SeverancePK id) {
        this.id = id;
    }
}
