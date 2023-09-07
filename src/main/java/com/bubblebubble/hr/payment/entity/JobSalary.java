package com.bubblebubble.hr.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_JOB_SALARY")
@Getter
@NoArgsConstructor
public class JobSalary {
    @Id
    @Column
    private String jobCode;

    @Column
    private int salary;

    public JobSalary(String jobCode, int salary) {
        this.jobCode = jobCode;
        this.salary = salary;
    }
}
