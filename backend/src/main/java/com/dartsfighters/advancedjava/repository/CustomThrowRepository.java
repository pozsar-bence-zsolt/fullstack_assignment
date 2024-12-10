package com.dartsfighters.advancedjava.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.dartsfighters.advancedjava.domain.User;

public interface CustomThrowRepository {
    List<User> findUsersByRowId(@Param("rowId") Integer rowId);
}
