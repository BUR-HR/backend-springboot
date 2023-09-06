package com.bubblebubble.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.payment.entity.Payroll;
import com.bubblebubble.hr.payment.entity.PayrollPK;

public interface PayrollRepository extends JpaRepository<Payroll, PayrollPK> {
    
}
