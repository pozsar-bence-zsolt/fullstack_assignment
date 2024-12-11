package com.dartsfighters.advancedjava.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OptionDto {
    private String value;
    private String label;

    public OptionDto(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
