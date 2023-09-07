package com.bubblebubble.hr.payment.dto;

import com.bubblebubble.hr.login.member.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterEmployeeEvent {
    
    private int empNo;
    private String jobCode;

    public RegisterEmployeeEvent(Employee employee) {
        this.empNo = employee.getEmpNo();
        this.jobCode = employee.getJobCode();
    }
}
