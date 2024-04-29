package com.example.service.impl;

import com.example.config.MonitorCallBack;
import com.example.config.RabbitConfig;
import com.example.pojo.Product;
import com.example.repository.StockRepository;
import com.example.service.ProductService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Administrator
 */
@Service
public class ProductServiceImpl implements ProductService{
    private final RabbitTemplate rabbitTemplate;
    private final StockRepository stockRepository;
    private final MonitorCallBack monitorCallBack;

    @PostConstruct
    @Scope("prototype")
    public void init(){
        rabbitTemplate.setConfirmCallback(monitorCallBack);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(monitorCallBack);
    }

    @Autowired
    public ProductServiceImpl(RabbitTemplate rabbitTemplate, StockRepository stockRepository, MonitorCallBack monitorCallBack) {
        this.rabbitTemplate = rabbitTemplate;
        this.stockRepository = stockRepository;
        this.monitorCallBack = monitorCallBack;
    }

    @Override
    public Product selectProductById(Integer id) {
        return new Product(1,"haha",2,123.00);
    }

    @Override
    public void changeStock() {

        CorrelationData correlationData = new CorrelationData("10086");
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME,"monitor","pause",correlationData);
    }
}
