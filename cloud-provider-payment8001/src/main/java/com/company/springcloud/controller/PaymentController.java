package com.company.springcloud.controller;

import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.entities.Payment;
import com.company.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
public class PaymentController {

    final private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("payment/create")
    public CommonResult<Payment> create(Payment payment){
       Payment payment1 = paymentService.add(payment);
       log.info("------result: "+ payment1);

       if (payment != null){
           return new CommonResult<>(200, "success");
       }else{
           return new CommonResult<>(444,"failed");
       }
    }

    @GetMapping("payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
     Payment payment = paymentService.findById(id);
     log.info("*****返回结果： "+payment);
     if (payment != null){
         return new CommonResult<>(200, "success");
     } else {
         return new CommonResult<>(444, "failed");
     }

    }




}
