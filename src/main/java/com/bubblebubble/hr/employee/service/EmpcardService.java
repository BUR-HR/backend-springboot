package com.bubblebubble.hr.employee.service;

import com.bubblebubble.hr.employee.repository.EmpcardRepository;
import com.bubblebubble.hr.login.member.entity.Employee;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmpcardService {
    private final EmpcardRepository empcardRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpcardService(EmpcardRepository empcardRepository, PasswordEncoder passwordEncoder) {
        this.empcardRepository = empcardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee registerEmployee(Employee employee, String temporaryPassword) {

        String savedPassword = passwordEncoder.encode(temporaryPassword);
        employee.setPassword(savedPassword); // 임시 비밀번호 설정
        employee.setIsEmployed('Y');
        System.out.println("[registerEmployee] employee = " + employee);
        return empcardRepository.save(employee);
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
