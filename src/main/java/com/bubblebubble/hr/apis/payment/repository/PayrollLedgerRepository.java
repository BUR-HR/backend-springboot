package com.bubblebubble.hr.apis.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.payment.entity.PayrollLedger;

public interface PayrollLedgerRepository extends JpaRepository<PayrollLedger, Integer> {

    List<PayrollLedger> findByNameOrderByNoDesc(String name);

    List<PayrollLedger> findByOrderByNoDesc();

}
