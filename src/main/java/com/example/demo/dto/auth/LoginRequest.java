package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {
    @NotBlank(message = "Email est obligatoire")
    private  String email;
    @NotBlank(message = "Password est obligatoire")
    private  String password;
}
