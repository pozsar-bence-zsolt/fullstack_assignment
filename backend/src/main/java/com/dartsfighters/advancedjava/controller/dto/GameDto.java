package com.dartsfighters.advancedjava.controller.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GameDto {
    private Integer id;
    private List<RowDto> rows;
    private Map<Integer, String> players;
    private Integer winner;

    public GameDto(Integer id, List<RowDto> rows, Map<Integer, String> players) {
        this.id = id;
        this.rows = rows;
        this.players = players;
    }
}