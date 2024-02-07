package com.company.springcloud.service;

import com.company.springcloud.entities.Payment;

/**
 * @author Administrator
 */
public interface PaymentService {
    public Payment add(Payment payment);
    public Payment findById(Long id);

}
