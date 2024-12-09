package com.dartsfighters.advancedjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dartsfighters.advancedjava.domain.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
    
}
