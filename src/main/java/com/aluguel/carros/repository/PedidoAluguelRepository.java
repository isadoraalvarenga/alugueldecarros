package com.aluguel.carros.repository;

import com.aluguel.carros.model.PedidoAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoAluguelRepository extends JpaRepository<PedidoAluguel, Long> {
    List<PedidoAluguel> findByClienteId(Long clienteId);
}