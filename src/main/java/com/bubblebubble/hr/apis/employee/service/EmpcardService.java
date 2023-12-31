package com.bubblebubble.hr.apis.employee.service;

import java.util.Random;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bubblebubble.hr.apis.employee.repository.EmpcardRepository;
import com.bubblebubble.hr.apis.login.dto.EmployeeDTO;
import com.bubblebubble.hr.apis.login.member.entity.Employee;
import com.bubblebubble.hr.apis.payment.event.RegisterEmployeeEvent;
import com.bubblebubble.hr.apis.payment.event.SendEmailEvent;

@Service
public class EmpcardService {
    private final EmpcardRepository empcardRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    public EmpcardService(EmpcardRepository empcardRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher) {
        this.empcardRepository = empcardRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    @Transactional
    public Employee registerEmployee(EmployeeDTO employee, String temporaryPassword) {

        Employee emp = empcardRepository.findById(employee.getEmpNo()).get();
        String savedPassword = passwordEncoder.encode(temporaryPassword);
        emp.setEmployeePassword(savedPassword);// 임시 비밀번호 설정
        System.out.println("[registerEmployee] employee = " + employee);
        employee.setEmpNo(emp.getEmpNo());

        applicationEventPublisher.publishEvent(new RegisterEmployeeEvent(emp));
        applicationEventPublisher.publishEvent(new SendEmailEvent(emp, temporaryPassword));

        return emp;
    }

    public static String generateTemporaryPassword() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int PASSWORD_LENGTH = 10;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
