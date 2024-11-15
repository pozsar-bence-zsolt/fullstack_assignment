package com.dartsfighters.advancedjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
    
    @GetMapping(value = "/test")
    public String Test() {
        return "test";
    }
}