package com.bubblebubble.hr.login.member.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TBL_EMP_AUTH")
@IdClass(EmployeeRolePK.class)
@Getter
@Setter
@ToString
public class EmployeeRole {

    @Id
    @Column(name = "EMP_NO")
    private int empNo;

    @Id
    @Column(name = "AUTH_CODE")
    private int authorityCode;

    /* Authority 타입의 속성은 조회할 때 Join용으로는 쓰지만 insert나 update할 때는 무시하라고 설정 */
    @ManyToOne
    @JoinColumn(name = "AUTH_CODE", insertable = false, updatable = false)
    private Authority authority;

    public EmployeeRole(){

    }

    public EmployeeRole(int empNo, int authorityCode) {
        this.empNo = empNo;
        this.authorityCode = authorityCode;
    }

    public EmployeeRole(int empNo, int authorityCode, Authority authority){
        this.empNo = empNo;
        this.authorityCode = authorityCode;
        this.authority = authority;
    }


}
