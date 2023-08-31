package com.bubblebubble.hr.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_PAYMENT_HISTORY")
@SequenceGenerator(sequenceName = "PAYMENT_SEQ", name = "SEQ_PAYMENT_NO")
@Getter
@NoArgsConstructor
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_SEQ")
    @Column(name = "PAYMENT_NO")
    private int paymentNo;

    @Column(name = "TOTAL_PAYMENT_AMOUNT")
    private int totalPaymentAmount;
    
    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Builder
    public PaymentHistory(int paymentNo, int totalPaymentAmount, String paymentType) {
        this.paymentNo = paymentNo;
        this.totalPaymentAmount = totalPaymentAmount;
        this.paymentType = paymentType;
    }

    public void setTotalPaymentAmount(int totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }
}
