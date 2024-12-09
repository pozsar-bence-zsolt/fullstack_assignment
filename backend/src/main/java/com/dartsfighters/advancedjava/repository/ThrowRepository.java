package com.dartsfighters.advancedjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dartsfighters.advancedjava.domain.Throw;

public interface ThrowRepository extends JpaRepository<Throw, Integer> {
    List<Throw> findByRowIdOrderByDartNumber(Integer rowId);
}
