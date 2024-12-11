package com.dartsfighters.advancedjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.dartsfighters.advancedjava.domain.Row;
import com.dartsfighters.advancedjava.domain.Throw;
import com.dartsfighters.advancedjava.domain.User;
import com.dartsfighters.advancedjava.repository.ThrowRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ThrowGroupingTest {

    @Autowired
    private ThrowRepository throwRepository;

    @Test
    @DisplayName("Group Throws by Row for a player")
    void testGroupThrowsByRowForPlayer() {
        Integer playerId = 1; // Example player ID
        User player = new User();
        player.setId(playerId);

        List<Throw> throwsList = throwRepository.findByThrowerOrderByDartNumber(player);

        assertNotNull(throwsList, "Throws should not be null");
        assertFalse(throwsList.isEmpty(), "Throws should not be empty");

        Map<Row, List<Throw>> rowToThrowsMap = new HashMap<>();
        for (Throw uthrow : throwsList) {
            Row row = uthrow.getRow();
            rowToThrowsMap.computeIfAbsent(row, k -> new ArrayList<>()).add(uthrow);
        }

        rowToThrowsMap.forEach((row, throwsInRow) -> {
            assertNotNull(row.getId(), "Row id should not be null");
            assertFalse(throwsInRow.isEmpty(), "Throws in row should not be empty");
        });
    }
}