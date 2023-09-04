package com.bubblebubble.hr.login.service;

import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.login.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO selectMyInfo(String empNo) {
        log.info("[MemberService] selectMyInfo Start ================= ");

        int empNoInt = Integer.parseInt(empNo); // empNo 값을 정수로 변환
        Employee employee = memberRepository.findByEmpNo(empNoInt);
        log.info("[MemberService] {} ============ ", employee);
        log.info("[MemberService] selectMyInfo End ============ ");
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Transactional
    public EmployeeDTO registerEmployee(EmployeeDTO employeeDTO) {
        // EmployeeDTO를 Employee 엔티티로 변환
        Employee registeredEmployee = modelMapper.map(employeeDTO, Employee.class);

        // Employee 엔티티를 데이터베이스에 저장하고 저장된 객체 반환
        Employee savedEmployee = memberRepository.save(registeredEmployee);
        System.out.println("savedEmployee = " + savedEmployee);
        log.info("savedEmployee {}" , savedEmployee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class); // 수정된 부분
    }

}