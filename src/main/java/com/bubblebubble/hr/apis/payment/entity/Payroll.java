package com.bubblebubble.hr.apis.payment.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TBL_PAYROLL")
@Getter
@NoArgsConstructor
@ToString
@DynamicInsert
public class Payroll {
    @EmbeddedId
    private PayrollPK paymentNo;

    @ManyToOne
    @JoinColumn(name = "PAYROLL_NO", insertable = false, updatable = false)
    private PayrollLedger payrollLedger;
    @Column(name = "SALARY")
    private int salary;
    @Column(name = "OVERTIME_PAY")
    private int overtimePay;
    @Column(name = "NATIONAL_PENSION")
    private int nationalPension;
    @Column(name = "HEALTH_INSURANCE")
    private int healthInsurance;
    @Column(name = "L_TERM_CARE_INSURE")
    private int longTermCareInsure;
    @Column(name = "EMP_INSURE")
    private int empInsure;
    @Column(name = "INCOME_TAX")
    private int incomeTax;
    @Column(name = "LOCAL_TAXES")
    private int localTaxes;
    @Column(name = "BONUS")
    private int bonus;
    @Column(name = "MEAL_ALLOWANCE")
    private int mealAllowance;
    
    public Payroll(PayrollPK paymentNo, int salary) {
        this.paymentNo = paymentNo;
        this.salary = salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    public void setOvertimePay(int overtimePay) {
        this.overtimePay = overtimePay;
    }
    public void setNationalPension(int nationalPension) {
        this.nationalPension = nationalPension;
    }
    public void setHealthInsurance(int healthInsurance) {
        this.healthInsurance = healthInsurance;
    }
    public void setLongTermCareInsure(int longTermCareInsure) {
        this.longTermCareInsure = longTermCareInsure;
    }
    public void setEmpInsure(int empInsure) {
        this.empInsure = empInsure;
    }
    public void setIncomeTax(int incomeTax) {
        this.incomeTax = incomeTax;
    }
    public void setLocalTaxes(int localTaxes) {
        this.localTaxes = localTaxes;
    }
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public void setMealAllowance(int mealAllowance) {
        this.mealAllowance = mealAllowance;
    }
}
