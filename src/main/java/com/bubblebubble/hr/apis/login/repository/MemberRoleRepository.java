package com.bubblebubble.hr.apis.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubblebubble.hr.apis.login.member.entity.EmployeeRole;
import com.bubblebubble.hr.apis.login.member.entity.EmployeeRolePK;

public interface MemberRoleRepository extends JpaRepository<EmployeeRole, EmployeeRolePK> {
}
