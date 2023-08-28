package com.bubblebubble.hr.login.service;

import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.login.member.entity.EmployeeRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserPrincipal implements UserDetails {

    private final Employee employee;

    public MyUserPrincipal(Employee employee, List<GrantedAuthority> authorities) {
        this.employee = employee;
    }

    // 직원의 권한 정보를 가져옴
    // 권한 정보는 EmployeeRole 엔티티의 컬렉션인 employee.getEmployeeRole()을 통해 가져옴
    // EmployeeRole에는 Authority 정보가 들어있으므로, 이를 바탕으로 GrantedAuthority 객체로 변환하여 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (EmployeeRole role : employee.getEmployeeRole()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority().getAuthName()));
            // SimpleGrantedAuthority는 Spring Security에서 권한을 나타내는 객체이다. ROLE_ 접두어를 붙여서 권한 이름을 생성하고 컬렉션에 추가함
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return employee.getEmployeePassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(employee.getEmpNo()); // 사원번호를 문자열로 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
