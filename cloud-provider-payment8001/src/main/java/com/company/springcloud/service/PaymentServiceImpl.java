package com.company.springcloud.service;

import com.company.springcloud.entities.Payment;
import com.company.springcloud.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    final private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment add(Payment payment){
        return paymentRepository.save(payment);
    }

    @Override
    public Payment findById(Long id){
        return paymentRepository.findById(id).get();
    }
}
