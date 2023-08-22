package com.bubblebubble.hr.login.repository;

import com.bubblebubble.hr.login.member.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Employee, Integer> {

    // 아이디(사원번호)와 비밀번호로 로그인 정보 조회
    Employee findByEmpNo(int empNo);

    Employee findByEmployeePassword(String password);
}

