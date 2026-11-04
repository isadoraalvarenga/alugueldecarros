package com.aluguel.carros.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PedidoAluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    private Automovel automovel;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.EM_ANALISE;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Automovel getAutomovel() { return automovel; }
    public void setAutomovel(Automovel automovel) { this.automovel = automovel; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}