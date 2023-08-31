package com.bubblebubble.hr.payment.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TBL_PAYROLL")
@Getter
@NoArgsConstructor
@ToString
public class Payroll {
    @Id
    long paymentNo;
}
