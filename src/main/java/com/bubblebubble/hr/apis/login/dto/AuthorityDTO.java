package com.bubblebubble.hr.apis.login.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthorityDTO {

    private int authorityCode;

    private String authorityName;
}
