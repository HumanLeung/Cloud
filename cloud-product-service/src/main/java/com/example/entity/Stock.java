package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 */
@Entity
@Table(name = "stock")
@Getter
@Setter
public class Stock {
    @Id
    private Integer ID;

    private Integer num;


}
