package com.playzone.api.controller;

import com.playzone.api.dto.request.JwtRequest;
import com.playzone.api.dto.request.RegistrationUserRequest;
import com.playzone.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Авторизироваться")
    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @Operation(summary = "Зарегистрироваться")
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid RegistrationUserRequest registrationUserRequest) {
        return authService.createNewUser(registrationUserRequest);
    }
}