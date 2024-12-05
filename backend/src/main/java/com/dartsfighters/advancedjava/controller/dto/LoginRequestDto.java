package com.dartsfighters.advancedjava.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

