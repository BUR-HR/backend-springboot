package com.bubblebubble.hr.apis.login.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private Integer jobCode;

    private String jobName;
}
