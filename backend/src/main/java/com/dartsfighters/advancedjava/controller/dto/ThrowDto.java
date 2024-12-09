package com.dartsfighters.advancedjava.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ThrowDto {
    private Integer dartNumber;
    private Integer score;
    private Integer userId;

    public ThrowDto(Integer dartNumber, Integer score, Integer userId) {
        this.dartNumber = dartNumber;
        this.score = score;
        this.userId = userId;
    }
}
