package com.aluguel.carros.service;

import com.aluguel.carros.model.Cliente;
import com.aluguel.carros.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente buscar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    public Cliente salvar(Cliente cliente) {
        cliente.getRendimentos().forEach(r -> r.setCliente(cliente));
        return repository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente dados) {
        Cliente existente = buscar(id);
        existente.setRg(dados.getRg());
        existente.setCpf(dados.getCpf());
        existente.setNome(dados.getNome());
        existente.setEndereco(dados.getEndereco());
        existente.setProfissao(dados.getProfissao());
        existente.getRendimentos().clear();
        dados.getRendimentos().forEach(r -> {
            r.setCliente(existente);
            existente.getRendimentos().add(r);
        });
        return repository.save(existente);
    }

    public void excluir(Long id) {
        repository.delete(buscar(id));
    }
}