package com.bubblebubble.hr.jwt.entity;

import javax.persistence.Id;

// @RedisHash(value = "refreshToken", timeToLive = 60)
public class RefreshToken {

    @Id
    private String refreshToken;
    private Integer empNo;

    public RefreshToken(String refreshToken, Integer empNo) {
        this.refreshToken = refreshToken;
        this.empNo = empNo;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    
}
