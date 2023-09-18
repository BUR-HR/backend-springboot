package com.bubblebubble.hr.apis.login.member.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeRolePK implements Serializable {

    private int empNo;

    private int authorityCode;
}
