package com.aluguel.carros.controller;

import com.aluguel.carros.model.Automovel;
import com.aluguel.carros.repository.AutomovelRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/automoveis")
@CrossOrigin(origins = "http://localhost:5173")
public class AutomovelController {
    private final AutomovelRepository repository;

    public AutomovelController(AutomovelRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Automovel> listar() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Automovel criar(@Valid @RequestBody Automovel automovel) {
        return repository.save(automovel);
    }
}