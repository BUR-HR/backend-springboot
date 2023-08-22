package com.bubblebubble.hr.login.service;

import com.bubblebubble.hr.login.jwt.TokenProvider;
import com.bubblebubble.hr.login.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;
}
