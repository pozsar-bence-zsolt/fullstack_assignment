package com.dartsfighters.advancedjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dartsfighters.advancedjava.domain.Throw;
import com.dartsfighters.advancedjava.domain.User;

public interface ThrowRepository extends JpaRepository<Throw, Integer> {
    List<Throw> findByRowIdOrderByDartNumber(Integer rowId);
    List<Throw> findByThrowerOrderByDartNumber(User thrower);
     
    @Query("SELECT t FROM Throw t WHERE t.thrower.id = :userId ORDER BY t.dartNumber")
    List<Throw> findThrowsByUser(@Param("userId") Integer userId);
}
