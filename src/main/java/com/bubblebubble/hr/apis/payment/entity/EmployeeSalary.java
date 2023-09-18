package com.bubblebubble.hr.apis.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TBL_EMP_SALARY")
@Getter
@NoArgsConstructor
@ToString
public class EmployeeSalary {
    @Id
    @Column(name = "EMP_NO")
    private int empNo;

    @Column(name = "SALARY")
    private int salary;

    public EmployeeSalary(int empNo, int salary) {
        this.empNo = empNo;
        this.salary = salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
