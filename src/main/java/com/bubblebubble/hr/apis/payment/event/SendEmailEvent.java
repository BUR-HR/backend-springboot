package com.bubblebubble.hr.apis.payment.event;

import com.bubblebubble.hr.apis.login.member.entity.Employee;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SendEmailEvent {
    private int empNo;
    private String temporaryPassword;
    private String email;

    public SendEmailEvent(Employee employee, String temporaryPassword) {
        this.empNo = employee.getEmpNo();
        this.email = employee.getEmployeeEmail();
        this.temporaryPassword = temporaryPassword;
    }
}
