package com.bubblebubble.hr.payment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bubblebubble.hr.payment.dto.PayrollLedgerResponseDTO;
import com.bubblebubble.hr.payment.entity.PayrollLedger;
import com.bubblebubble.hr.payment.repository.PayrollLedgerRepository;
import com.bubblebubble.hr.payment.repository.PayrollRepository;
import com.bubblebubble.hr.payment.repository.SeveranceLedgerRepository;
import com.bubblebubble.hr.payment.repository.SeveranceRepository;

@Service
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


        return payrollLedger.stream().map(item -> modelMapper.map(item, PayrollLedgerResponseDTO.class)).collect(Collectors.toList());
    }
    
}
