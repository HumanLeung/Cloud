package com.company.springcloud.service;

import com.company.springcloud.entities.Payment;

public interface PaymentService {
    public Payment add(Payment payment);
    public Payment findById(Long id);

}
