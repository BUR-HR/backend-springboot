package com.bubblebubble.hr.apis.payment.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.bubblebubble.hr.apis.payment.dto.SeveranceLedgerInsertRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TBL_SEVERANCE_LEDGER")
@SequenceGenerator(sequenceName = "SEQ_SEVERANCE_NO", name = "SEQ_TBL_SEVERANCE_NO", allocationSize = 1, initialValue = 1)
@NoArgsConstructor
@Getter
@DynamicInsert
@ToString
public class SeveranceLedger {
    @Id
    @Column(name = "SEVERANCE_NO")
    @GeneratedValue(generator = "SEQ_TBL_SEVERANCE_NO", strategy = GenerationType.SEQUENCE)
    private int no;
    @Column(name = "SEVERANCE_NAME")
    private String name;
    @Column(name = "PAYMENT_SCHEDULED_DATE")
    private LocalDate paymentScheduledDate;
    @Column(name = "SALARY_BASE_START_DATE")
    private LocalDate salaryBaseStartDate;
    @Column(name = "SALARY_BASE_END_DATE")
    private LocalDate salaryBaseEndDate;
    @Column(name = "TOTAL_SALARY_AMOUNT")
    private int totalSalaryAmount;
    @Column(name = "TOTAL_AMOUNT_TAX")
    private int totalAmountTax;
    @Column(name = "NET_INCOME")
    private int netIncome;
    @Column(name = "CREATE_DATE")
    private LocalDate createDate;
    @Column(name = "IS_CLOSED")
    private String isClosed;
    @Column(name = "PAYMENT_TYPE")
    private String paymentType;
    @Column(name = "COUNT")
    private int count;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "severanceLedger")
    private List<Severance> severances = new ArrayList<>();

    @Builder
    public SeveranceLedger(int no, String name, LocalDate paymentScheduledDate, LocalDate salaryBaseStartDate,
            LocalDate salaryBaseEndDate, int totalSalaryAmount, int totalAmountTax, int netIncome,
            LocalDate createDate, String isClosed) {
        this.no = no;
        this.name = name;
        this.paymentScheduledDate = paymentScheduledDate;
        this.salaryBaseStartDate = salaryBaseStartDate;
        this.salaryBaseEndDate = salaryBaseEndDate;
        this.totalSalaryAmount = totalSalaryAmount;
        this.totalAmountTax = totalAmountTax;
        this.netIncome = netIncome;
        this.createDate = createDate;
        this.isClosed = isClosed;
    }

    public SeveranceLedger(SeveranceLedgerInsertRequestDTO request) {
        this.name = request.getName();
        this.salaryBaseStartDate = request.getSalaryBaseStartDate();
        this.salaryBaseEndDate = request.getSalaryBaseEndDate();
        this.paymentScheduledDate = request.getPaymentScheduledDate();
        this.paymentType = request.getPaymentType();
        this.createDate = LocalDate.now();
    }

    public void addSeverance(Severance severance) {
        severances.add(severance);
    }

    public void setTotalSalaryAmount(int totalSalaryAmount) {
        this.totalSalaryAmount = totalSalaryAmount;
    }

    public void setTotalAmountTax(int totalAmountTax) {
        this.totalAmountTax = totalAmountTax;
    }

    public void setNetIncome(int netIncome) {
        this.netIncome = netIncome;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
