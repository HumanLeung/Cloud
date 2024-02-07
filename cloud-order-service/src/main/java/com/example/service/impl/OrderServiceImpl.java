package com.example.service.impl;

import com.example.pojo.Order;
import com.example.service.OrderProductService;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Administrator
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderProductService orderProductService;



    @Override
    public Order selectOrderById(Integer id) {
        return new Order(id,"order-001","zh",2345.02, Collections.singletonList(orderProductService.selectProductById(1)));
    }
}
