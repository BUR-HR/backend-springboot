package com.bubblebubble.hr.login.member.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TBL_EMPLOYEE")
@SequenceGenerator(
        name = "EMPLOYEE_SEQ_GENERATOR",
        sequenceName = "SEQ_EMP_NO",
        initialValue = 1, allocationSize = 1

)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

    @Id
    @Column(name = "EMP_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "EMPLOYEE_SEQ_GENERATOR"
    )
    private int empNo;

    @Column(name = "EMP_NAME")
    private String empName;
    @Column(name = "DEPT_CODE")
    private String deptCode;

    @Column(name = "JOB_CODE")
    private String jobCode;

    @Column(name = "RSDN")
    private String employeeRsdn;

    @Column(name = "EMAIL")
    private String employeeEmail;

    @Column(name = "PASSWORD")
    private String employeePassword;

    @Column(name = "HIRE_DATE")
    private Date hireDate;

    @Column(name = "PHONE")
    private String employeePhone;

    @Column(name = "ADDRESS")
    private String employeeAddress;

    @Column(name = "PAYROLL_ACCOUNT")
    private String payrollAccount;

    @Column(name = "IS_EMPLOYED")
    private boolean isEmployed;

    @Column(name = "STATUS")
    private String employeeStatus;

    @Column(name = "GENDER")
    private String employeeGender;

    @Column(name = "LEAVE_DATE")
    private Date leaveDate;

    @OneToMany
    @JoinColumn(name = "EMP_NO")
    private List<EmployeeRole> employeeRole;

    @Override
    public String toString() {
        return "Employee [empNo=" + empNo + ", empName=" + empName + ", deptCode =" + deptCode
                + ", jobCode=" + jobCode + ", employeeRsdn=" + employeeRsdn + ", employeeEmail="
                + employeeEmail + ", employeePassword=" + employeePassword + ", hireDate=" + hireDate
                + ", employeePhone=" + employeePhone + ", employeeAddress=" + employeeAddress +
                ", payrollAccount=" + payrollAccount + ", isEmployed=" + isEmployed + ", employeeStatus="
                + employeeStatus + ", employeeGender=" + employeeGender + ", leaveDate=" + leaveDate +
                ", employeeRoles=" + employeeRole + "]";
        }
    }
