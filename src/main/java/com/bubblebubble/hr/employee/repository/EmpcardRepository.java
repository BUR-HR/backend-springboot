package com.bubblebubble.hr.employee.repository;

import com.bubblebubble.hr.login.member.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpcardRepository extends JpaRepository<Employee, Long> {
    // 필요한 추가 메서드 선언
}
