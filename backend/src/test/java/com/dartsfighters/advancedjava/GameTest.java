package com.dartsfighters.advancedjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.dartsfighters.advancedjava.domain.Game;
import com.dartsfighters.advancedjava.repository.GameRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    @DisplayName("Find Games by Player id")
    void testFindGamesByPlayer() {
        Integer playerId = 1; // Example player ID
        List<Game> games = gameRepository.findGamesByPlayer(playerId);

        assertNotNull(games, "Games should not be null");
        assertFalse(games.isEmpty(), "Games should not be empty");
        games.forEach(game -> {
            assertNotNull(game.getId(), "Game id should not be null");
        });
    }
}