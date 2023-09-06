package com.bubblebubble.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.payment.entity.Severance;
import com.bubblebubble.hr.payment.entity.SeverancePK;

public interface SeveranceRepository extends JpaRepository<Severance, SeverancePK> {
    
}
