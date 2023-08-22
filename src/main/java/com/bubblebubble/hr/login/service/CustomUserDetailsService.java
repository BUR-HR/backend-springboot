package com.bubblebubble.hr.login.service;

import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.login.member.entity.EmployeeRole;
import com.bubblebubble.hr.login.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Employee employee = memberRepository.findByEmployeeNo(empNo);
        EmployeeDTO employeeDTO = ModelMapper.map(employee, EmployeeDTO.class);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(EmployeeRole employeeRole : employee.getEmployeeRoles()){
            String authorityName = employeeRole.getAuthority().getAuthName();
            authorities.add(new SimpleGrantedAuthority(authorityName));
        }

        employeeDTO.setAuthorities(authorities);

        return employeeDTO;
    }
}
