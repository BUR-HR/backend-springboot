package com.bubblebubble.hr.payment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bubblebubble.hr.payment.dto.PayrollLedgerInsertRequestDTO;
import com.bubblebubble.hr.payment.dto.PayrollLedgerResponseDTO;
import com.bubblebubble.hr.payment.dto.SeveranceLedgerInsertRequestDTO;
import com.bubblebubble.hr.payment.dto.SeveranceLedgerResponseDTO;
import com.bubblebubble.hr.payment.entity.PayrollLedger;
import com.bubblebubble.hr.payment.entity.SeveranceLedger;
import com.bubblebubble.hr.payment.repository.PayrollLedgerRepository;
import com.bubblebubble.hr.payment.repository.PayrollRepository;
import com.bubblebubble.hr.payment.repository.SeveranceLedgerRepository;
import com.bubblebubble.hr.payment.repository.SeveranceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {

    private final ModelMapper modelMapper;
    private final PayrollRepository payrollRepository;
    private final SeveranceRepository severanceRepository;
    private final PayrollLedgerRepository payrollLedgerRepository;
    private final SeveranceLedgerRepository severanceLedgerRepository;

    public PaymentService(ModelMapper modelMapper, PayrollRepository payrollRepository,
            SeveranceRepository severanceRepository, PayrollLedgerRepository payrollLedgerRepository,
            SeveranceLedgerRepository severanceLedgerRepository) {
        this.modelMapper = modelMapper;
        this.payrollRepository = payrollRepository;
        this.severanceRepository = severanceRepository;
        this.payrollLedgerRepository = payrollLedgerRepository;
        this.severanceLedgerRepository = severanceLedgerRepository;
    }

    public List<PayrollLedgerResponseDTO> getPayrollList(String name) {
        List<PayrollLedger> payrollLedger = null;
        if (name.isEmpty())
            payrollLedger = payrollLedgerRepository.findAll();
        else
            payrollLedger = payrollLedgerRepository.findByName(name);

        return payrollLedger.stream().map(item -> modelMapper.map(item, PayrollLedgerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PayrollLedgerResponseDTO insertPayroll(PayrollLedgerInsertRequestDTO payrollRequest) {
        PayrollLedger payrollLedger = new PayrollLedger(payrollRequest);
        log.info("PayrollLedger {}", payrollLedger);

        PayrollLedger result = payrollLedgerRepository.save(payrollLedger);

        log.info("result {}", result);

        return modelMapper.map(result, PayrollLedgerResponseDTO.class);
    }

    public SeveranceLedgerResponseDTO insertSeverance(SeveranceLedgerInsertRequestDTO severanceRequest) {
        SeveranceLedger severanceLedger = new SeveranceLedger(severanceRequest);
        log.info("severance {}", severanceLedger);

        SeveranceLedger result = severanceLedgerRepository.save(severanceLedger);

        log.info("result {}", result);

        return modelMapper.map(result, SeveranceLedgerResponseDTO.class);
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

}
