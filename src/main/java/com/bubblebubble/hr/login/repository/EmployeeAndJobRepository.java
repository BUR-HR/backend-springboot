package com.bubblebubble.hr.login.repository;

import com.bubblebubble.hr.login.member.entity.EmployeeAndJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeAndJobRepository extends JpaRepository<EmployeeAndJob, Integer> {

    List<EmployeeAndJob> findByJobJobCode(int jobCode);
}
