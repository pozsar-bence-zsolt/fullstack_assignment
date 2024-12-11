package com.dartsfighters.advancedjava.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ThrowDto {
    private Integer throwId;
    private Integer dartNumber;
    private Integer score;
    private Integer userId;

    public ThrowDto(Integer throwId, Integer dartNumber, Integer score, Integer userId) {
        this.throwId = throwId;
        this.dartNumber = dartNumber;
        this.score = score;
        this.userId = userId;
    }
}
