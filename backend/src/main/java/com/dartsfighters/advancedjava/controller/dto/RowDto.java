package com.dartsfighters.advancedjava.controller.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class RowDto {
    private Integer id;
    private List<ThrowDto> throwsList;

    public RowDto(Integer id, List<ThrowDto> throwsList) {
        this.id = id;
        this.throwsList = throwsList;
    }
}
