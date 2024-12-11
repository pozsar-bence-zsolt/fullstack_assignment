package com.dartsfighters.advancedjava.controller.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LobbyReadyDto {
    @NotBlank
    private List<String> players;
}
