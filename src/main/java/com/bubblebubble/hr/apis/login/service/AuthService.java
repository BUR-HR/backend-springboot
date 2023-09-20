package com.bubblebubble.hr.apis.login.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bubblebubble.hr.apis.login.dto.EmployeeDTO;
import com.bubblebubble.hr.apis.login.dto.TokenDTO;
import com.bubblebubble.hr.apis.login.member.entity.Employee;
import com.bubblebubble.hr.apis.login.member.entity.EmployeeRole;
import com.bubblebubble.hr.apis.login.repository.MemberRepository;
import com.bubblebubble.hr.exception.LoginFailedException;
import com.bubblebubble.hr.jwt.TokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final ModelMapper modelMapper;


    public AuthService(MemberRepository memberRepository
                        , PasswordEncoder passwordEncoder
                        , TokenProvider tokenProvider
                        , ModelMapper modelMapper){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
    }

    // 로그인 작업 수행
    @Transactional
    public Object login(EmployeeDTO employeeDTO, HttpServletResponse response) { // MemberDTO 객체로 받은 로그인 정보를 처리

        log.info("[AuthService] login Start ==================================");
        log.info("[AuthService] {} ================== ", employeeDTO);
        /* 1. 아이디 조회 */
        Employee employee = memberRepository.findByEmpNo(employeeDTO.getEmpNo());

        log.info("[AuthService] member 조회 {} ================== ", employee);

        if (employee == null) {
            throw new LoginFailedException(employeeDTO.getEmpNo() + "를 찾을 수 없습니다.");
        }

        /* 2. 비밀번호 매칭
         * passwordEncoder.matches(평문, 다이제스트)
         * */
        if (!passwordEncoder.matches(employeeDTO.getEmployeePassword(), employee.getEmployeePassword())) {
            log.info("[AuthService] Password Match Fail! ");
            throw new LoginFailedException("잘못된 비밀번호 입니다.");
        }

        /* 3. 토큰 발급 */
        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(employee, response);

        log.info("[AuthService] tokenDTO {} =======> ", tokenDTO);
        log.info("[AuthService] login End ==================================");

        return tokenDTO;   // 로그인 성공 시 토큰을 생성하여 TokenDTO 객체로 반환
    }

    public Object reissuanceAccessToken(EmployeeDTO employeeDTO) {
        Employee employee = memberRepository.findByEmpNo(employeeDTO.getEmpNo());

        TokenDTO accessToken = tokenProvider.reissuanceAccessToken(employee);
        return accessToken;
    }


}