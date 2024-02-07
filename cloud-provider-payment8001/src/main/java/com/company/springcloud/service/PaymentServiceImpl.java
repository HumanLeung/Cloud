package com.company.springcloud.service;

import com.company.springcloud.entities.Payment;
import com.company.springcloud.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Administrator
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

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
        Payment payment = new Payment();
        payment.setId(123214L);
        payment.setSerial(UUID.randomUUID().toString());
        return paymentRepository.findById(id).orElse(payment);
    }
}
