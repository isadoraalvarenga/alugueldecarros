package com.aluguel.carros.controller;

import com.aluguel.carros.dto.ContratoRequest;
import com.aluguel.carros.model.ContratoCredito;
import com.aluguel.carros.repository.ContratoCreditoRepository;
import com.aluguel.carros.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pedidos/{pedidoId}/contrato")
public class ContratoCreditoController {
    private final ContratoCreditoRepository contratos;
    private final PedidoService pedidos;

    public ContratoCreditoController(ContratoCreditoRepository contratos, PedidoService pedidos) {
        this.contratos = contratos;
        this.pedidos = pedidos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratoCredito criar(@PathVariable Long pedidoId, @Valid @RequestBody ContratoRequest req) {
        if (contratos.findByPedidoId(pedidoId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pedido já possui contrato");
        }
        ContratoCredito contrato = new ContratoCredito();
        contrato.setPedido(pedidos.buscar(pedidoId));
        contrato.setValor(req.valor());
        contrato.setBanco(req.banco());
        return contratos.save(contrato);
    }

    @GetMapping
    public ContratoCredito buscar(@PathVariable Long pedidoId) {
        return contratos.findByPedidoId(pedidoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contrato não encontrado"));
    }
}