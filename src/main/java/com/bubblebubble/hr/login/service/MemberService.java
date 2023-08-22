package com.bubblebubble.hr.login.service;

import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.login.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper){
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


}
