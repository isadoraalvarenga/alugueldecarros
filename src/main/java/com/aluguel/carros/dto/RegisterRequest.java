package com.aluguel.carros.dto;

import com.aluguel.carros.model.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank String login,
    @NotBlank @Size(min = 6) String senha,
    @NotNull Perfil perfil) {
}