package com.aluguel.carros.service;

import com.aluguel.carros.dto.PedidoRequest;
import com.aluguel.carros.model.*;
import com.aluguel.carros.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoAluguelRepository pedidos;
    private final ClienteRepository clientes;
    private final AutomovelRepository automoveis;

    public PedidoService(PedidoAluguelRepository pedidos, ClienteRepository clientes, AutomovelRepository automoveis) {
        this.pedidos = pedidos;
        this.clientes = clientes;
        this.automoveis = automoveis;
    }

    public PedidoAluguel criar(PedidoRequest req) {
        Cliente cliente = clientes.findById(req.clienteId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        Automovel automovel = automoveis.findById(req.automovelId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Automóvel não encontrado"));
        PedidoAluguel pedido = new PedidoAluguel();
        pedido.setCliente(cliente);
        pedido.setAutomovel(automovel);
        return pedidos.save(pedido);
    }

    public List<PedidoAluguel> listar(Long clienteId) {
        return clienteId == null ? pedidos.findAll() : pedidos.findByClienteId(clienteId);
    }

    public PedidoAluguel buscar(Long id) {
        return pedidos.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    public PedidoAluguel alterarStatus(Long id, StatusPedido status) {
        PedidoAluguel pedido = buscar(id);
        pedido.setStatus(status);
        return pedidos.save(pedido);
    }

    public PedidoAluguel cancelar(Long id) {
        PedidoAluguel pedido = buscar(id);
        if (pedido.getStatus() == StatusPedido.APROVADO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pedido aprovado não pode ser cancelado");
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidos.save(pedido);
    }
}