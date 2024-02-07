package com.example.service.impl;

import com.example.pojo.Product;
import com.example.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class ProductServiceImpl implements ProductService{
    @Override
    public Product selectProductById(Integer id) {
        return new Product(1,"haha",2,123.00);
    }
}
