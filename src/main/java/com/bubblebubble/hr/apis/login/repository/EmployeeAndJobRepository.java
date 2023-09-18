package com.bubblebubble.hr.apis.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.login.member.entity.EmployeeAndJob;

import java.util.List;

public interface EmployeeAndJobRepository extends JpaRepository<EmployeeAndJob, Integer> {

    List<EmployeeAndJob> findByJobJobCodeOrderByDeptAsc(int jobCode);
}
