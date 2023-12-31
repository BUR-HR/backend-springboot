package com.bubblebubble.hr.apis.login.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bubblebubble.hr.apis.login.dto.EmployeeDTO;
import com.bubblebubble.hr.apis.login.member.entity.Employee;
import com.bubblebubble.hr.apis.login.member.entity.EmployeeRole;
import com.bubblebubble.hr.apis.login.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    public CustomUserDetailsService(MemberRepository memberRepository,
                                    ModelMapper modelMapper){
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException{

        Employee employee = memberRepository.findByEmpNo(Integer.parseInt(empNo));
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(EmployeeRole employeeRole : employee.getEmployeeRole()){
            String authorityName = employeeRole.getAuthority().getAuthName();
            authorities.add(new SimpleGrantedAuthority(authorityName));
        }

        employeeDTO.setAuthorities(authorities);

        return employeeDTO;
    }
}
