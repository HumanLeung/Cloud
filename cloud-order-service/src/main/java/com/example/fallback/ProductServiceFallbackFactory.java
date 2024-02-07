package com.example.fallback;

import com.example.pojo.Product;
import com.example.service.OrderProductService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class ProductServiceFallbackFactory implements FallbackFactory<OrderProductService> {
    @Override
    public OrderProductService create(Throwable throwable) {
        return id -> new Product(id,"failed",2,666.21);
    }
}
