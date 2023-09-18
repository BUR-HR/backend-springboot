package com.bubblebubble.hr.apis.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bubblebubble.hr.apis.payment.dto.RegisterEmployeeEvent;
import com.bubblebubble.hr.apis.payment.entity.EmployeeSalary;
import com.bubblebubble.hr.apis.payment.entity.JobSalary;
import com.bubblebubble.hr.apis.payment.repository.EmployeeSalaryRepository;
import com.bubblebubble.hr.apis.payment.repository.JobSalaryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalaryService {
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final JobSalaryRepository jobSalaryRepository;

    public SalaryService(EmployeeSalaryRepository employeeSalaryRepository,
            JobSalaryRepository jobSalaryRepository) {
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.jobSalaryRepository = jobSalaryRepository;
    }
    
    @Transactional
    public EmployeeSalary insertDefaultSalary(RegisterEmployeeEvent event) {
        log.info("[RegisterEmployeeEvent] start =================================");
        JobSalary jobSalary = jobSalaryRepository.findByJobCode(event.getJobCode());
        EmployeeSalary employeeSalary = new EmployeeSalary(event.getEmpNo(), jobSalary.getSalary());

        EmployeeSalary result = employeeSalaryRepository.saveAndFlush(employeeSalary);
        log.info("result {}", result);
        log.info("[RegisterEmployeeEvent] end =================================");

        return result;
    }
}
