package com.bubblebubble.hr.login.dto;

import com.bubblebubble.hr.login.member.entity.Department;
import com.bubblebubble.hr.login.member.entity.Job;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeAndJobDTO {


    private int empNo;

    private String empName;

    private Department dept;

    private Job job;

    private String employeeRsdn;

    private String employeeEmail;

    private String employeePassword;

    private Date hireDate;

    private String employeePhone;

    private String employeeAddress;

    private String payrollAccount;

    private Character isEmployed;

    private String employeeStatus;

    private String employeeGender;

    private Date leaveDate;

    private String originFile;

    private String renameFile;

    private String bank;

}
