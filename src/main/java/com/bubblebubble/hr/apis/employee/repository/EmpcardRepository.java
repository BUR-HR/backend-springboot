package com.bubblebubble.hr.apis.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.login.member.entity.Employee;

public interface EmpcardRepository extends JpaRepository<Employee, Integer> {
    // 필요한 추가 메서드 선언
}
