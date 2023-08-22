package com.bubblebubble.hr.login.member.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_AUTHORITY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Authority {

    @Id
    @Column(name = "AUTH_CODE")
    private int authCode;

    @Column(name = "AUTH_NAME")
    private String authName;
}
