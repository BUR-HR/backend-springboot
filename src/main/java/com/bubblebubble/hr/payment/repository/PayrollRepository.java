package com.bubblebubble.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.payment.entity.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    
}
