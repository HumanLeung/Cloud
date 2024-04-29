package com.company.springcloud.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
public class Payment {
    private long id;
    private String serial;
    private Integer money;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "serial")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getMoney() {
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && Objects.equals(serial, payment.serial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serial);
    }
}
