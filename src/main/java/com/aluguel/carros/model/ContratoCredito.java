package com.aluguel.carros.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class ContratoCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    private String banco;

    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", unique = true)
    @JsonIgnore
    private PedidoAluguel pedido;

    public Long getId() { return id; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public PedidoAluguel getPedido() { return pedido; }
    public void setPedido(PedidoAluguel pedido) { this.pedido = pedido; }
}