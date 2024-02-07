package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private Integer id;
    private String productName;
    private Integer productNum;
    private Double productPrice;
}
