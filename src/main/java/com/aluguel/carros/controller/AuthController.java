package com.aluguel.carros.controller;

import com.aluguel.carros.dto.LoginRequest;
import com.aluguel.carros.dto.LoginResponse;
import com.aluguel.carros.dto.RegisterRequest;
import com.aluguel.carros.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar(@Valid @RequestBody RegisterRequest req) {
        service.cadastrar(req);
    }

    @PostMapping("/login")
    public LoginResponse entrar(@Valid @RequestBody LoginRequest req) {
        return service.entrar(req);
    }
}