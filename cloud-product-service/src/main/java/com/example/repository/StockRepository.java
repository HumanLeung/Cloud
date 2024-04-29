package com.example.repository;

import com.example.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {

    /**
     * asd
     * @param id
     * @param num
     */
    @Modifying
    @Query(value = "UPDATE Stock SET num = ?2 where ID = ?1")
    void updateStock(int id, int num);
}
