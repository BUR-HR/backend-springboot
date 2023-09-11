package com.bubblebubble.hr.login.member.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
