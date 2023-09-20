package com.bubblebubble.hr.apis.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bubblebubble.hr.apis.payment.entity.Payroll;
import com.bubblebubble.hr.apis.payment.entity.PayrollPK;

public interface PayrollRepository extends JpaRepository<Payroll, PayrollPK> {

    @Query("SELECT p, m, j, d " +
            "from Payroll p " +
            "left join p.employeeAndJob m " +
            "left join m.dept d " +
            "left join m.job j " +
            "WHERE p.payroll.payrollNo = :no " +
            "ORDER by p.payroll.payrollNo asc")
    List<Payroll> findByPayrollNo(@Param("no") Integer no);
    
}
