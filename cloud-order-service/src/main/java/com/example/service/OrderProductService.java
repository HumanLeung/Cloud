package com.example.service;

import com.example.fallback.ProductServiceFallbackFactory;
import com.example.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Administrator
 */
@Component
@FeignClient(value = "CLOUD-PRODUCT-SERVICE")
public interface OrderProductService {


    /**
     * asd
     * @param id
     * @return
     */
    @GetMapping("/product/{id}")
    Product selectProductById(@PathVariable("id") Integer id);

}
