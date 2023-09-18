package com.bubblebubble.hr.apis.payment.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.payment.entity.Payroll;
import com.bubblebubble.hr.apis.payment.entity.PayrollPK;

public interface PayrollRepository extends JpaRepository<Payroll, PayrollPK> {

    List<Payroll> findByPayrollPayrollNo(Integer no, Sort by);
    
}
