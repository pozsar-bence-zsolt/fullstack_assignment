package com.dartsfighters.advancedjava.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dartsfighters.advancedjava.domain.Row;

public interface RowRepository extends JpaRepository<Row, Integer> {
    List<Row> findByGameId(Integer gameId);

    @Query("SELECT r FROM Row r WHERE r.game.id = :gameId AND r.rowNumber = " +
        "(SELECT MAX(rt.rowNumber) FROM Row rt WHERE rt.game.id = :gameId)")
    Optional<Row> findMaxRowNumberByGameId(@Param("gameId") Integer gameId);
}
