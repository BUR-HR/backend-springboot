package com.bubblebubble.hr.payment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PayrollPK implements Serializable {
    @Column(name = "EMP_NO")
    private int empNo;
    @Column(name = "PAYROLL_NO")
    private int severanceNo;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + empNo;
        result = prime * result + severanceNo;
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
        if (severanceNo != other.severanceNo)
            return false;
        return true;
    }

}
