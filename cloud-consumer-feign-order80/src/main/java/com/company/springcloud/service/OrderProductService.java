package com.company.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 */
@Component
@FeignClient(value = "CLOUD-PRODUCT-SERVICE")
public interface OrderProductService {


    @GetMapping("/product/stock")
    String stock(@RequestParam("tId") String tId);

}
