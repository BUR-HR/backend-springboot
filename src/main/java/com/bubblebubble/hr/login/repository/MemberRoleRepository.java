package com.bubblebubble.hr.login.repository;

import com.bubblebubble.hr.login.member.entity.EmployeeRole;
import com.bubblebubble.hr.login.member.entity.EmployeeRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<EmployeeRole, EmployeeRolePK> {
}
