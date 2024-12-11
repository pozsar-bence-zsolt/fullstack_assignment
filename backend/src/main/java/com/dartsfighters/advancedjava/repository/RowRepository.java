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

    @Query("SELECT DISTINCT r FROM Row r LEFT JOIN r.throwsList t WHERE t.thrower.id = :userId")
    List<Row> findRowsByUser(@Param("userId") Integer userId);
}
