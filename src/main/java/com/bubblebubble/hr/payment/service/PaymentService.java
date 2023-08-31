package com.bubblebubble.hr.payment.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bubblebubble.hr.payment.repository.PayrollRepository;
import com.bubblebubble.hr.payment.repository.SeveranceRepository;

@Service
public class PaymentService {
    
    private final ModelMapper modelMapper;
    private final PayrollRepository payrollRepository;
    private final SeveranceRepository severanceRepository;

    public PaymentService(ModelMapper modelMapper, PayrollRepository payrollRepository,
            SeveranceRepository severanceRepository) {
        this.modelMapper = modelMapper;
        this.payrollRepository = payrollRepository;
        this.severanceRepository = severanceRepository;
    }
    
}
