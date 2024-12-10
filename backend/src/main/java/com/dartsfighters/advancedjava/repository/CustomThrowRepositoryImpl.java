package com.dartsfighters.advancedjava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.dartsfighters.advancedjava.domain.Throw;
import com.dartsfighters.advancedjava.domain.User;

import java.util.ArrayList;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class CustomThrowRepositoryImpl implements CustomThrowRepository {

    @PersistenceContext
    EntityManager entityManager;

    private final ThrowRepository throwRepository;

    @Override
    @Transactional
    public List<User> findUsersByRowId(Integer rowId) {
        List<Throw> uthrows = this.throwRepository.findByRowIdOrderByDartNumber(rowId);
        List<User> players = new ArrayList<>();
        for (Throw uthrow : uthrows) {
            User player = uthrow.getThrower();
            if (!players.contains(player)) {
                players.add(player);
            }
        }

        return players;
    }
}

