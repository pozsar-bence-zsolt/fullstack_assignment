package com.dartsfighters.advancedjava.controller.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.dartsfighters.advancedjava.domain.UserRole;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private Integer id;
    private String email;
    private String username;
    private String role;

    public static final Map<String, String> ROLE_MAP;

    static {
        Map<String, String> map = new HashMap<>();
        map.put(UserRole.ROLE_ADMIN, "admin");
        map.put(UserRole.ROLE_USER, "user");
        ROLE_MAP = Collections.unmodifiableMap(map);
    }

    public UserDto(
        Integer id,
        String email,
        String username,
        String role
    ) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = ROLE_MAP.get(role);
    }
}