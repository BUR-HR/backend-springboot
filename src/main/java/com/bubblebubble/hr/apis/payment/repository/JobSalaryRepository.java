package com.bubblebubble.hr.apis.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.payment.entity.JobSalary;

public interface JobSalaryRepository extends JpaRepository<JobSalary, String> {

    JobSalary findByJobCode(String jobCode);
    
}
