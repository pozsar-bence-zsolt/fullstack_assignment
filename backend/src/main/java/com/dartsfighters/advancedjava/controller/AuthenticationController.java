package com.dartsfighters.advancedjava.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dartsfighters.advancedjava.controller.dto.LoginRequestDto;
import com.dartsfighters.advancedjava.controller.dto.RegisterRequestDto;
import com.dartsfighters.advancedjava.domain.User;
import com.dartsfighters.advancedjava.repository.UserRepository;
import com.dartsfighters.advancedjava.security.JWTUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(newUser);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("status", "success");
        successResponse.put("message", "User registered successfully");
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@Valid @RequestBody LoginRequestDto loginRequest){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(loginRequest.getUsername());

            return Collections.singletonMap("jwt-token", token);
        }catch (AuthenticationException authExc){
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> profile(Principal principal){
        Map<String, String> response = new HashMap<>();
        String username = principal.getName();
        User user = this.userRepository.findByUsername(username).get();
        response.put("userId", user.getId().toString());
        response.put("username", username);
        response.put("role", user.getRole());
        return ResponseEntity.ok(response);
    }
}
