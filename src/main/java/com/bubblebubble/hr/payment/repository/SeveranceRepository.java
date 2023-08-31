package com.bubblebubble.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.payment.entity.Severance;

public interface SeveranceRepository extends JpaRepository<Severance, Long> {
    
}
