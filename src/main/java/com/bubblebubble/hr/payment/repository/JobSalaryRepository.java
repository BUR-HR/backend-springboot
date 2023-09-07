package com.bubblebubble.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.payment.entity.JobSalary;

public interface JobSalaryRepository extends JpaRepository<JobSalary, String> {

    JobSalary findByJobCode(String jobCode);
    
}
