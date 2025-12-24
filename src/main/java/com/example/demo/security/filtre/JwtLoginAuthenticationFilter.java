package com.example.demo.security.filtre;

import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.entity.UserApp;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public JwtLoginAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            LoginRequest login = mapper.readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword()
                    );

            return authenticationManager.authenticate(authToken);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        UserApp userApp = userDetails.getUserApp();

        String email = userApp.getEmail();

        Set<String> permissions = userApp.getUserPermissions() != null
                ? userApp.getUserPermissions().stream()
                .map(userPermission -> userPermission.getPermission().getCode())
                .collect(Collectors.toSet())
                : Collections.emptySet();

        String token = jwtUtil.generateToken(email, permissions);

        String role = userApp.getRole() != null
                ? userApp.getRole().getName().name()
                : "NO_ROLE";

        String permsJson = permissions.stream()
                .map(p -> "\"" + p + "\"")
                .collect(Collectors.joining(", "));

        response.setContentType("application/json");
        response.getWriter().write("""
        {
          "token": "%s",
          "email": "%s",
          "role": "%s",
          "permissions": [%s]
        }
        """.formatted(token, email, role, permsJson));
    }


    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("""
        {
          "error": "Authentication failed",
          "message": "%s"
        }
        """.formatted(failed.getMessage()));
    }
}
