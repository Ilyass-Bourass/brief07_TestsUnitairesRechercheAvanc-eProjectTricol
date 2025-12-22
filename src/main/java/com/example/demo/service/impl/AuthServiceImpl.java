package com.example.demo.service.impl;

import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.entity.UserApp;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.repository.UserAppRepository;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {
    private final UserAppRepository userAppRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> register(RegisterRequest registerRequest) {

        if(userAppRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new DuplicateResourceException(List.of("Un utilisateur avec cet email existe déjà"));
        }
        UserApp userApp = new UserApp();
        userApp.setUsername(registerRequest.getUsername());
        userApp.setEmail(registerRequest.getEmail());
        userApp.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userAppRepository.save(userApp);

        return Map.of("message", "utilsateur enregistre avec succes",
                "email", registerRequest.getEmail());
    }
}
