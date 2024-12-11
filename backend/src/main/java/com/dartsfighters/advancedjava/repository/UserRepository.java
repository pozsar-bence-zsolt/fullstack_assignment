package com.dartsfighters.advancedjava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dartsfighters.advancedjava.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
