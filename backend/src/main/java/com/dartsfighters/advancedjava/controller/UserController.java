package com.dartsfighters.advancedjava.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dartsfighters.advancedjava.controller.dto.OptionDto;
import com.dartsfighters.advancedjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/options")
    public List<OptionDto> getOptions() {
        return userRepository.findAll().stream()
            .map(user -> new OptionDto(user.getId().toString(), user.getUsername()))
            .collect(Collectors.toList());
    }
    
}
