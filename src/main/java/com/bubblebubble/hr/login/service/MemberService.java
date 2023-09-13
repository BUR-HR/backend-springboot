package com.bubblebubble.hr.login.service;

import com.bubblebubble.hr.login.dto.EmployeeAndJobDTO;
import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.login.member.entity.EmployeeAndJob;
import com.bubblebubble.hr.login.repository.EmployeeAndJobRepository;
import com.bubblebubble.hr.login.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    private final EmployeeAndJobRepository employeeAndJobRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper,EmployeeAndJobRepository employeeAndJobRepository) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.employeeAndJobRepository = employeeAndJobRepository;
    }


    // 전체 직원 목록 조회
    public List<EmployeeDTO> getAllEmployees() {

        List<Employee> employees = memberRepository.findAll(); // 전체 직원 조회
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
            employeeDTOs.add(employeeDTO);
        }

        return employeeDTOs;
    }


    // empName를 받아서 직원 조회
    public EmployeeDTO selectEmployeeByName(String empName) {
        log.info("[MemberService] selectMyInfo Start ================= ");

        Employee employee = memberRepository.findByEmpName(empName);
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


    public Map<String, List<EmployeeAndJobDTO>> getEmpAllList() {

        List<EmployeeAndJob> tempList  = employeeAndJobRepository.findByJobJobCode(2);
        System.out.println("tempList = " + tempList);
        List<EmployeeAndJob> dempList =  employeeAndJobRepository.findByJobJobCode(3);
        System.out.println("dempList = " + dempList);
        List<EmployeeAndJob> empList = employeeAndJobRepository.findByJobJobCode(4);
        System.out.println("empList = " + empList);

        List<EmployeeAndJobDTO> tempDTO = tempList.stream().map(temp -> modelMapper.map(temp, EmployeeAndJobDTO.class)).collect(Collectors.toList());
        List<EmployeeAndJobDTO> dempDTO = dempList.stream().map(dempt -> modelMapper.map(dempt, EmployeeAndJobDTO.class)).collect(Collectors.toList());
        List<EmployeeAndJobDTO> empDTO = empList.stream().map(emp-> modelMapper.map(emp, EmployeeAndJobDTO.class)).collect(Collectors.toList());

        Map<String, List<EmployeeAndJobDTO>> map = new HashMap<>();
        map.put("temp", tempDTO);
        map.put("demp", dempDTO);
        map.put("emp", empDTO);

        return map;


    }
}