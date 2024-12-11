package com.dartsfighters.advancedjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.dartsfighters.advancedjava.domain.Row;
import com.dartsfighters.advancedjava.domain.Throw;
import com.dartsfighters.advancedjava.repository.RowRepository;
import com.dartsfighters.advancedjava.repository.ThrowRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RowTest {

    @Autowired
    private RowRepository rowRepository;
    @Autowired
    private ThrowRepository throwRepository;

    @Test
    @DisplayName("Find Rows and Throws by Game id")
    void testFindRowsAndThrowsByGameId() {
        Integer gameId = 1; // Example gameId
        List<Row> rows = rowRepository.findByGameId(gameId);

        assertNotNull(rows, "Rows should not be null");
        assertFalse(rows.isEmpty(), "Rows should not be empty");

        rows.forEach(row -> {
            assertNotNull(row.getId(), "Row id should not be null");

            List<Throw> throwsList = throwRepository.findByRowIdOrderByDartNumber(row.getId());
            assertNotNull(throwsList, "Throws should not be null");
            throwsList.forEach(uthrow -> {
                assertNotNull(uthrow.getId(), "Throw id should not be null");
                assertNotNull(uthrow.getDartNumber(), "Dart number should not be null");
                assertNotNull(uthrow.getThrower(), "Thrower should not be null");
            });
        });
    }
}