package com.bubblebubble.hr.apis.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.login.member.entity.Employee;

import java.util.List;

public interface MemberRepository extends JpaRepository<Employee, Integer> {

    // 아이디(사원번호)와 비밀번호로 로그인 정보 조회
    Employee findByEmpNo(int empNo);

    // 사원이름
    Employee findByEmpName(String empName);

    Employee findByEmployeePassword(String password);

    List<Employee> findAll();


}

