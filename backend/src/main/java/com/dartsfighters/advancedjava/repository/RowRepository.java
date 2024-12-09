package com.dartsfighters.advancedjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dartsfighters.advancedjava.domain.Row;

public interface RowRepository extends JpaRepository<Row, Integer> {
    List<Row> findByGameId(Integer gameId);
}
