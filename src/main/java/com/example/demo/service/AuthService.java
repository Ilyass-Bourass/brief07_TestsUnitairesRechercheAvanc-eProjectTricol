package com.example.demo.service;

import com.example.demo.dto.auth.RegisterRequest;

import java.util.Map;

public interface AuthService {
    Map<String,String> register(RegisterRequest registerRequest);
}
