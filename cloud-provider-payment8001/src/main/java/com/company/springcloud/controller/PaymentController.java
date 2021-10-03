package com.company.springcloud.controller;

import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.entities.Payment;
import com.company.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
@Slf4j
public class PaymentController {

    final private PaymentService paymentService;
    final private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public PaymentController(PaymentService paymentService, DiscoveryClient discoveryClient){
        this.paymentService = paymentService;
        this.discoveryClient = discoveryClient;
    }


    @PostMapping("payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
       Payment payment1 = paymentService.add(payment);
       log.info("------result: "+ payment1);

       if (payment != null){
           return new CommonResult<>(200, "success.serverPost: "+serverPort,payment);
       }else{
           return new CommonResult<>(444,"failed");
       }
    }

    @GetMapping("payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
     Payment payment = paymentService.findById(id);
     log.info("*****返回结果： "+payment);
     if (payment != null){
         return new CommonResult<>(200, "success, serverPost: "+serverPort,payment);
     } else {
         return new CommonResult<>(444, "failed");
     }
    }

    @GetMapping(value = "payment/discovery")
    public Object discovery(){
     List<String> services = discoveryClient.getServices();
     for (String element : services) {
         log.info("*******element: "+element);
     }
    List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
    for (ServiceInstance instance : instances){
        log.info(instance.getServiceId()+"\t"+instance.getInstanceId()+"\t"+instance.getHost()
        +instance.getUri());
    }
    return this.discoveryClient;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout()
    {
        // 业务逻辑处理正确，但是需要耗费3秒钟
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return serverPort;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverPort;
    }



}
