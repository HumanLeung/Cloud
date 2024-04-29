package org.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Administrator
 */



@Entity
@Table(name = "stage")
@Getter
@Setter
public class Stage {
    @Id
    private String ID;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "WAITER_COUNT")
    private Integer waiterCount;

    @Column(name = "FINISH_COUNT")
    private Integer finishCount;
    @Column(name = "TID")
    private String tId;
    @Transient
    private String action;

}
