package com.company.springcloud.service;

import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author HumanLeung
 * @create 2021/10/2 21:10
 */
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    /**
     * test
     * @param id
     * @return
     */
    @GetMapping(value = "/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    /**
     * test
     * @return
     */
    @GetMapping(value = "/payment/feign/timeout")
    String paymentFeignTimeout();
}
