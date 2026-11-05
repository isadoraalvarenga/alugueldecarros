package com.aluguel.carros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ContratoRequest(@NotNull BigDecimal valor, @NotBlank String banco) {
}