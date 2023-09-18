package com.bubblebubble.hr.apis.payment.dto;

import com.bubblebubble.hr.apis.payment.entity.Payroll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayrollViewDTO {
    private int empNo;
    private String empName;
    private String jobName;
    private String deptName;
    private int salary;
    private int overtimePay;
    private int nationalPension;
    private int healthInsurance;
    private int longTermCareInsure;
    private int empInsure;
    private int incomeTax;
    private int localTaxes;
    private int bonus;
    private int mealAllowance;
    private int totalIncome;
    private int totalDeductions;
    private int netIncome;

    public PayrollViewDTO(Payroll payroll) {
        this.empNo = payroll.getPayroll().getEmpNo();
        this.empName = payroll.getEmployeeAndJob().getEmpName();
        this.jobName = payroll.getEmployeeAndJob().getJob().getJobName();
        this.deptName = payroll.getEmployeeAndJob().getDept().getDeptName();
        this.salary = payroll.getSalary();
        this.overtimePay = payroll.getOvertimePay();
        this.nationalPension = payroll.getNationalPension();
        this.healthInsurance = payroll.getHealthInsurance();
        this.longTermCareInsure = payroll.getLongTermCareInsure();
        this.empInsure = payroll.getEmpInsure();
        this.incomeTax = payroll.getIncomeTax();
        this.localTaxes = payroll.getLocalTaxes();
        this.bonus = payroll.getBonus();
        this.mealAllowance = payroll.getMealAllowance();
        this.totalIncome = salary + overtimePay + mealAllowance + bonus;
        this.totalDeductions = empInsure + incomeTax + nationalPension + healthInsurance + longTermCareInsure;
        this.netIncome = totalIncome - totalDeductions;
    }
}
