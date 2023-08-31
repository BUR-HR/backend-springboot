package com.bubblebubble.hr.payment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SeveracePK implements Serializable {
    @Column(name = "EMP_NO")
    private int empNo;
    @Column(name = "SEVERANCE_NO")
    private int severanceNo;
    @Column(name = "PAYMENT_NO")
    private int paymentNo;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + empNo;
        result = prime * result + severanceNo;
        result = prime * result + paymentNo;
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
        SeveracePK other = (SeveracePK) obj;
        if (empNo != other.empNo)
            return false;
        if (severanceNo != other.severanceNo)
            return false;
        if (paymentNo != other.paymentNo)
            return false;
        return true;
    }

    
}
