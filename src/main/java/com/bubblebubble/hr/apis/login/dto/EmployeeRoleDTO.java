package com.bubblebubble.hr.apis.login.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeRoleDTO {

    private int empNo;

    private int authorityCode;

    private AuthorityDTO authority;

    public EmployeeRoleDTO(){

    }

    public EmployeeRoleDTO(int empNo, int authorityCode){
        this.empNo = empNo;
        this.authorityCode = authorityCode;
    }

    public EmployeeRoleDTO(int empNo, int authorityCode, AuthorityDTO authority) {
        this.empNo = empNo;
        this.authorityCode = authorityCode;
        this.authority = authority;
    }
}
