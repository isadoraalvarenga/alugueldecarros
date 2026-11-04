package com.aluguel.carros.dto;

import jakarta.validation.constraints.NotNull;

public record PedidoRequest(@NotNull Long clienteId, @NotNull Long automovelId) {
}