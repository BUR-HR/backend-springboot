package com.bubblebubble.hr.apis.login.member.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@DynamicInsert
@DynamicUpdate
public class EmployeeAndJob {

    @Id
    @Column(name = "EMP_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "EMPLOYEE_SEQ_GENERATOR"
    )
    private int empNo;

    @Column(name = "EMP_NAME")
    private String empName;

    @ManyToOne
    @JoinColumn(name = "DEPT_CODE")
    private Department dept;

    @ManyToOne
    @JoinColumn(name = "JOB_CODE")
    private Job job;

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
    private Character isEmployed;

    @Column(name = "STATUS")
    private String employeeStatus;


    @Column(name = "GENDER")
    private String employeeGender;

    @Column(name = "LEAVE_DATE")
    private Date leaveDate;

    @Column(name = "ORIGINFILE")
    private String originFile;

    @Column(name = "RENAMEFILE")
    private String renameFile;

    @Column(name = "BANK")
    private String bank;

}
