package com.company.springcloud.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.company.springcloud.config.MonitorCallBack;
import com.company.springcloud.config.RabbitConfig;
import com.company.springcloud.entities.CommonResult;
import com.company.springcloud.entities.Payment;
import com.company.springcloud.entity.Stage;
import com.company.springcloud.service.OrderProductService;
import com.company.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author HumanLeung
 * @create 2021/10/2 21:17
 */
@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;
    private final MonitorCallBack monitorCallBack;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private OrderProductService orderProductService;

    public OrderFeignController(MonitorCallBack monitorCallBack) {
        this.monitorCallBack = monitorCallBack;
    }

    @PostConstruct
    @Scope("prototype")
    public void init(){
        rabbitTemplate.setConfirmCallback(monitorCallBack);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(monitorCallBack);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        System.out.println(paymentFeignService);
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout()
    {
        // OpenFeign客户端一般默认等待1秒钟
        return paymentFeignService.paymentFeignTimeout();
    }


    @GetMapping("consumer/save")
    public String createOrder(){
        CorrelationData correlationData = new CorrelationData("10085");
        Stage stage = new Stage();
        stage.setID(UUID.randomUUID().toString());
        stage.setAction("start");
        stage.setTId(UUID.randomUUID().toString());
        stage.setFinishCount(2);
        stage.setWaiterCount(2);
        stage.setStartTime(new Date());
        String jsonString = JSON.toJSONString(stage);

        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME,"monitor",jsonString,correlationData);

        orderProductService.stock(stage.getTId());

        paymentFeignService.payRightNow(stage.getTId());

        return "done!";
    }
}
