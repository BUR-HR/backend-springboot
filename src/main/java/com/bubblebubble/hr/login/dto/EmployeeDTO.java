package com.bubblebubble.hr.login.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO implements UserDetails {
    private int empNo;

    private String empName;

    private String deptCode;

    private String jobCode;

    private String employeeRsdn;

    private String employeeEmail;

    private String employeePassword;

    private Date hireDate;

    private String employeePhone;

    private String employeeAddress;

    private String payrollAcoount;

    private boolean isEmployed;

    private String employeeStatus;

    private String employeeGender;

    private Date leaveDate;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return this.employeeEmail; // Assuming email is used as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        // Add your implementation here
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Add your implementation here
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Add your implementation here
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Add your implementation here
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.employeePassword;
    }

}

