package com.example.demo.dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegisterRequest {

    @NotBlank(message = "Username est obligatoire")
    private String username;

    @NotBlank(message = "Email est obligatoire")
    private String email;

    @NotBlank(message = "Password est obligatoire")
    private String password;
}
