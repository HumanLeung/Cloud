package com.company.springcloud.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Administrator
 */



@Getter
@Setter
public class Stage {

    @Id
    private String ID;

    private Date startTime;

    private Integer waiterCount;

    private Integer finishCount;
    private String tId;
    private String action;

}
