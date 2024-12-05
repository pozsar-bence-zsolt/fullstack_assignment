package com.dartsfighters.advancedjava.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegisterRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String repassword;
}

