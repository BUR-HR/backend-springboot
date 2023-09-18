package com.bubblebubble.hr.apis.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.payment.entity.EmployeeSalary;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Integer> {
    
}