package com.company.springcloud.controller;

import com.company.springcloud.config.RabbitConfig;
import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.entities.Payment;
import com.company.springcloud.repository.PaymentRepository;
import com.company.springcloud.service.PaymentService;
import com.company.springcloud.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@RestController
@RequestMapping
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final PaymentRepository paymentRepository;
    private final DiscoveryClient discoveryClient;
    private final RabbitTemplate rabbitTemplate;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public PaymentController(PaymentService paymentService, TransactionService transactionService, PaymentRepository paymentRepository, DiscoveryClient discoveryClient, RabbitTemplate rabbitTemplate){
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.paymentRepository = paymentRepository;
        this.discoveryClient = discoveryClient;
        this.rabbitTemplate = rabbitTemplate;
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

    @GetMapping(value = "/payment/feign/pay")
    @Transactional
    public String payRightNow(@RequestParam("tid") String tid){
        boolean ready = false;
        boolean isAllCheck = false;
        do {
            ready = checkRegister(tid);
        }while (!ready);
        System.out.println("prepare-pay");
        rabbitTemplate.convertAndSend("monitor.finish","finish",tid);
        do {
            isAllCheck = transactionService.isAllCheck(tid);
        }while (!isAllCheck);
        System.out.println("allCheck");
        paymentRepository.updatePay(2,14);
        System.out.println("pay-done!");
//        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME,"monitor","payment");

        return "h";
//        throw new RuntimeException("asdad");
    }

    private boolean checkRegister(String id){
        return transactionService.isRegister(id);
    }



}
