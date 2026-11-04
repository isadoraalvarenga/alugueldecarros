package com.aluguel.carros.controller;

import com.aluguel.carros.dto.PedidoRequest;
import com.aluguel.carros.model.PedidoAluguel;
import com.aluguel.carros.model.StatusPedido;
import com.aluguel.carros.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoAluguel criar(@Valid @RequestBody PedidoRequest req) {
        return service.criar(req);
    }

    @GetMapping
    public List<PedidoAluguel> listar(@RequestParam(required = false) Long clienteId) {
        return service.listar(clienteId);
    }

    @GetMapping("/{id}")
    public PedidoAluguel buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PatchMapping("/{id}/status")
    public PedidoAluguel alterarStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        return service.alterarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public PedidoAluguel cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}