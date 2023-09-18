package com.bubblebubble.hr.apis.payment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bubblebubble.hr.apis.payment.dto.PayrollLedgerInsertRequestDTO;
import com.bubblebubble.hr.apis.payment.dto.PayrollLedgerResponseDTO;
import com.bubblebubble.hr.apis.payment.dto.PayrollViewDTO;
import com.bubblebubble.hr.apis.payment.dto.SeveranceLedgerInsertRequestDTO;
import com.bubblebubble.hr.apis.payment.dto.SeveranceLedgerResponseDTO;
import com.bubblebubble.hr.apis.payment.entity.EmployeeSalary;
import com.bubblebubble.hr.apis.payment.entity.Payroll;
import com.bubblebubble.hr.apis.payment.entity.PayrollLedger;
import com.bubblebubble.hr.apis.payment.entity.PayrollPK;
import com.bubblebubble.hr.apis.payment.entity.Severance;
import com.bubblebubble.hr.apis.payment.entity.SeveranceLedger;
import com.bubblebubble.hr.apis.payment.entity.SeverancePK;
import com.bubblebubble.hr.apis.payment.repository.EmployeeSalaryRepository;
import com.bubblebubble.hr.apis.payment.repository.PayrollLedgerRepository;
import com.bubblebubble.hr.apis.payment.repository.PayrollRepository;
import com.bubblebubble.hr.apis.payment.repository.SeveranceLedgerRepository;
import com.bubblebubble.hr.apis.payment.repository.SeveranceRepository;
import com.bubblebubble.hr.exception.NotFoundEmployeeSalaryException;
import com.bubblebubble.hr.exception.NotFoundPayrollLedgerException;
import com.bubblebubble.hr.exception.NotFoundSeveranceLedgerException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {

    private final ModelMapper modelMapper;
    private final PayrollRepository payrollRepository;
    private final SeveranceRepository severanceRepository;
    private final PayrollLedgerRepository payrollLedgerRepository;
    private final SeveranceLedgerRepository severanceLedgerRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;

    public PaymentService(ModelMapper modelMapper, PayrollRepository payrollRepository,
            SeveranceRepository severanceRepository, PayrollLedgerRepository payrollLedgerRepository,
            SeveranceLedgerRepository severanceLedgerRepository, EmployeeSalaryRepository employeeSalaryRepository) {
        this.modelMapper = modelMapper;
        this.payrollRepository = payrollRepository;
        this.severanceRepository = severanceRepository;
        this.payrollLedgerRepository = payrollLedgerRepository;
        this.severanceLedgerRepository = severanceLedgerRepository;
        this.employeeSalaryRepository = employeeSalaryRepository;
    }

    public List<PayrollLedgerResponseDTO> getPayrollList(String name) {
        List<PayrollLedger> payrollLedger = null;
        if (name.isEmpty())
            payrollLedger = payrollLedgerRepository.findByOrderByNoDesc();
        else
            payrollLedger = payrollLedgerRepository.findByNameOrderByNoDesc(name);

        return payrollLedger.stream().map(item -> modelMapper.map(item, PayrollLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PayrollLedgerResponseDTO> insertPayroll(PayrollLedgerInsertRequestDTO payrollRequest)
            throws IllegalArgumentException, NotFoundEmployeeSalaryException {
        PayrollLedger payrollLedger = new PayrollLedger(payrollRequest);
        List<EmployeeSalary> employeeSalary = employeeSalaryRepository.findAllById(payrollRequest.getEmpNo());

        if (payrollRequest.getEmpNo().size() != employeeSalary.size()) {
            throw new IllegalArgumentException();
        }

        PayrollLedger result = payrollLedgerRepository.save(payrollLedger);

        employeeSalary.forEach(emp -> {
            Payroll payroll = new Payroll(new PayrollPK(emp.getEmpNo(), result.getNo()), emp.getSalary());
            result.addPayroll(payroll);
        });

        result.setCount(employeeSalary.size());
        log.info("result {}", result);

        List<PayrollLedger> payrollLedgers = payrollLedgerRepository.findByOrderByNoDesc();

        return payrollLedgers.stream().map(item -> modelMapper.map(item, PayrollLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<SeveranceLedgerResponseDTO> insertSeverance(SeveranceLedgerInsertRequestDTO severanceRequest) {
        SeveranceLedger severanceLedger = new SeveranceLedger(severanceRequest);
        List<EmployeeSalary> employeeSalary = employeeSalaryRepository.findAllById(severanceRequest.getEmpNo());

        if (severanceRequest.getEmpNo().size() != employeeSalary.size()) {
            throw new IllegalArgumentException();
        }

        SeveranceLedger result = severanceLedgerRepository.save(severanceLedger);

        employeeSalary.forEach(emp -> {
            Severance severance = new Severance(new SeverancePK(emp.getEmpNo(), result.getNo()));
            result.addSeverance(severance);
        });

        result.setCount(employeeSalary.size());
        log.info("result {}", result);

        List<SeveranceLedger> SeveranceLedgers = severanceLedgerRepository.findByOrderByNoDesc();

        return SeveranceLedgers.stream().map(item -> modelMapper.map(item, SeveranceLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<SeveranceLedgerResponseDTO> getSeveranceList(String name) {
        List<SeveranceLedger> severanceLedger = null;
        if (name.isEmpty())
            severanceLedger = severanceLedgerRepository.findAll();
        else
            severanceLedger = severanceLedgerRepository.findByName(name);

        return severanceLedger.stream().map(item -> modelMapper.map(item, SeveranceLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PayrollLedgerResponseDTO> deletePayroll(int no)
            throws NotFoundPayrollLedgerException, IllegalAccessException {
        log.info("no {}", no);
        PayrollLedger payrollLedger = payrollLedgerRepository.findById(no)
                .orElseThrow(() -> new NotFoundPayrollLedgerException("해당하는 급여대장을 찾을 수 없습니다."));

        if (!payrollLedger.getIsClosed().equals("N")) {
            throw new IllegalAccessException("이미 마감된 급여대장입니다");
        }

        payrollLedgerRepository.deleteById(no);

        List<PayrollLedger> payrollLedgers = payrollLedgerRepository.findByOrderByNoDesc();

        return payrollLedgers.stream().map(item -> modelMapper.map(item, PayrollLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PayrollLedgerResponseDTO> closePayrollLedger(int no) throws NotFoundPayrollLedgerException {
        PayrollLedger payrollLedger = payrollLedgerRepository.findById(no)
                .orElseThrow(() -> new NotFoundPayrollLedgerException("해당하는 급여대장을 찾을 수 없습니다."));

        payrollLedger.setIsClosed("Y");

        List<PayrollLedger> payrollLedgers = payrollLedgerRepository.findByOrderByNoDesc();
        return payrollLedgers.stream().map(item -> modelMapper.map(item, PayrollLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SeveranceLedgerResponseDTO> deleteSeverance(int no)
            throws NotFoundSeveranceLedgerException, IllegalAccessException {
        log.info("no {}", no);
        SeveranceLedger severanceLedger = severanceLedgerRepository.findById(no)
                .orElseThrow(() -> new NotFoundSeveranceLedgerException("해당하는 퇴직금대장을 찾을 수 없습니다."));

        if (!severanceLedger.getIsClosed().equals("N")) {
            throw new IllegalAccessException("이미 마감된 급여대장입니다");
        }

        severanceLedgerRepository.deleteById(no);

        List<SeveranceLedger> severanceLedgers = severanceLedgerRepository.findByOrderByNoDesc();

        return severanceLedgers.stream().map(item -> modelMapper.map(item, SeveranceLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SeveranceLedgerResponseDTO> closeSeveranceLedger(int no) throws NotFoundSeveranceLedgerException {
        SeveranceLedger severanceLedger = severanceLedgerRepository.findById(no)
                .orElseThrow(() -> new NotFoundSeveranceLedgerException("해당하는 급여대장을 찾을 수 없습니다."));

        severanceLedger.setIsClosed("Y");

        List<SeveranceLedger> severanceLedgers = severanceLedgerRepository.findByOrderByNoDesc();

        return severanceLedgers.stream().map(item -> modelMapper.map(item, SeveranceLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<PayrollViewDTO> selectPayroll(Integer no) {
        log.info("dsaifwjeinbjfdfnbvjksdfs");
        List<Payroll> payrolls = payrollRepository.findByPayrollPayrollNo(no, Sort.by(Direction.ASC, "payrollEmpNo"));
        return payrolls.stream().map(PayrollViewDTO::new).collect(Collectors.toList());
    }

}
