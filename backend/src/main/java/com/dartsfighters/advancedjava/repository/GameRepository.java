package com.dartsfighters.advancedjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dartsfighters.advancedjava.domain.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
    @Query(value = "select distinct u.id, u.username from game g " +
                "left join \"row\" r on r.game_id = g.id " +
                "left join throw t on t.row_id = r.id " +
                "left join users u on t.thrower_id = u.id " +
                "where game_id = :gameId", nativeQuery = true)
    List<Object[]> findPlayers(@Param("gameId") Integer gameId);

    @Query("SELECT DISTINCT g FROM Game g " +
        "JOIN g.rows r " +
        "JOIN r.throwsList t " +
        "WHERE t.thrower.id = :playerId")
    List<Game> findGamesByPlayer(@Param("playerId") Integer playerId);
}
