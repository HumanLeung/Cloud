package com.example.controller;


import com.example.pojo.Product;
import com.example.service.ProductService;
import com.example.service.TransactionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final RabbitTemplate rabbitTemplate;
    private final TransactionService transactionService;

    @Autowired
    public ProductController(ProductService productService, RabbitTemplate rabbitTemplate, TransactionService transactionService) {
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
        this.transactionService = transactionService;
    }


    @GetMapping("/{id}")
    public Product selectProductById(@PathVariable("id") Integer id){
        return productService.selectProductById(id);
    }

    @GetMapping("/stock")
    @Transactional(rollbackFor = Exception.class)
    public String stock(@RequestParam("tId") String tId){
        boolean ready = false;
        boolean isAllCheck = false;
        do {
           ready = checkRegister(tId);
        }while (!ready);
        System.out.println("prepare");
        rabbitTemplate.convertAndSend("monitor.finish","finish",tId);
        do {
            isAllCheck = transactionService.isAllCheck(tId);
        }while (!isAllCheck);
        System.out.println("allCheck");
        productService.changeStock();
        System.out.println("stock-done!");
        return "good";
    }

    private boolean checkRegister(String id){
        return transactionService.isRegister(id);
    }
}
