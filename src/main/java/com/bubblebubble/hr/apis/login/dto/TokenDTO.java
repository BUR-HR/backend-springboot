package com.bubblebubble.hr.apis.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class TokenDTO {

    private String grantType; // 토큰타입
    private String EmployeeName; // 인증받은 직원 이름
    private String accessToken; // 엑세스 토큰
    private Long accessTokenExpiresIn; // Long 형의 만료 시간
}
