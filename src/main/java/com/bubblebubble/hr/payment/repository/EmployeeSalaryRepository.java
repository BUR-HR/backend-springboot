package com.bubblebubble.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.payment.entity.EmployeeSalary;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Integer> {
    
}