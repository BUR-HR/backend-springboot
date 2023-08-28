package com.bubblebubble.hr.login.service;

import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.login.member.entity.EmployeeRole;
import com.bubblebubble.hr.login.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {
        Employee employee = memberRepository.findByEmpNo(Integer.parseInt(empNo));
        if (employee == null) {
            throw new UsernameNotFoundException("해당 직원을 찾을 수 없습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (EmployeeRole role : employee.getEmployeeRole()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority().getAuthName()));
        }
        return new MyUserPrincipal(employee, authorities);
    }
}