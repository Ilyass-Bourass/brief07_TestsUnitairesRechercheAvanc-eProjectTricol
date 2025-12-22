package com.example.demo.controller;


import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.entity.UserApp;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> regisetr(@Valid @RequestBody RegisterRequest registerRequest){
        Map<String,String> response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }
}
