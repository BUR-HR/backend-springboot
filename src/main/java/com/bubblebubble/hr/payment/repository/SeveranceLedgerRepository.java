package com.bubblebubble.hr.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.payment.entity.SeveranceLedger;

public interface SeveranceLedgerRepository extends JpaRepository<SeveranceLedger, Integer>  {

    List<SeveranceLedger> findByName(String name);

    List<SeveranceLedger> findByOrderByNoDesc();

}
