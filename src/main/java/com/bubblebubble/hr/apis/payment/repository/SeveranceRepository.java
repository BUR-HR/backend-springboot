package com.bubblebubble.hr.apis.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.payment.entity.Severance;
import com.bubblebubble.hr.apis.payment.entity.SeverancePK;

public interface SeveranceRepository extends JpaRepository<Severance, SeverancePK> {
    
}
