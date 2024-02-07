package com.example.service;

import com.example.pojo.Product;

/**
 * @author Administrator
 */

public interface ProductService {

    /**
     * @param id
     * @return
     */
    Product selectProductById(Integer id);
}
