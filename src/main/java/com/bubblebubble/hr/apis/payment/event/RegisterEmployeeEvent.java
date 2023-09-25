package com.bubblebubble.hr.apis.payment.event;

import com.bubblebubble.hr.apis.login.member.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
