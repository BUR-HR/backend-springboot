package com.bubblebubble.hr.apis.login.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBL_DEPARTMENT")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @Column(name = "DEPT_CODE")
    private Integer deptCode;

    @Column(name="DEPT_NAME")
    private String deptName;
}
