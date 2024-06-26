package com.company.springcloud.repository;

import com.company.springcloud.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    /**
     * @param id
     * @param money
     */
    @Modifying
    @Query(value = "UPDATE Payment SET money = ?2 where id = ?1")
    void updatePay(long id, int money);

}
