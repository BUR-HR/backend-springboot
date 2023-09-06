package com.bubblebubble.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.payment.entity.PayrollLedger;

public interface PayrollLedgerRepository extends JpaRepository<PayrollLedger, Integer> {
    
}
