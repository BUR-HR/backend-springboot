package com.bubblebubble.hr.login.member.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_JOB")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @Column(name="JOB_CODE")
    private int jobCode;

    @Column(name="JOB_NAME")
    private String jobName;
}
