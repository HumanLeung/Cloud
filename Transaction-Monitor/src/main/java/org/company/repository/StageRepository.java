package org.company.repository;

import org.company.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Administrator
 */
@Repository
public interface StageRepository extends JpaRepository<Stage,String> {


    /**
     * @param tid
     * @return
     */
    @Query(value = "SELECT * FROM stage s WHERE TID = ?1" ,nativeQuery = true)
    public Optional<Stage> findByTId(String tid);
}
