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

    public EmployeeDTO selectMyInfo(String memberId) {
        log.info("[MemberService] selectMyInfo");
    }


}
