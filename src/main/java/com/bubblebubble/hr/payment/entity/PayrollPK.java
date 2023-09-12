package com.bubblebubble.hr.payment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PayrollPK implements Serializable {
    @Column(name = "EMP_NO")
    private int empNo;
    @Column(name = "PAYROLL_NO")
    private int payrollNo;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + empNo;
        result = prime * result + payrollNo;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PayrollPK other = (PayrollPK) obj;
        if (empNo != other.empNo)
            return false;
        if (payrollNo != other.payrollNo)
            return false;
        return true;
    }

}
