package com.aluguel.carros.service;

import com.aluguel.carros.dto.LoginRequest;
import com.aluguel.carros.dto.LoginResponse;
import com.aluguel.carros.dto.RegisterRequest;
import com.aluguel.carros.model.Usuario;
import com.aluguel.carros.repository.UsuarioRepository;
import com.aluguel.carros.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarios, PasswordEncoder encoder, JwtService jwtService) {
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public void cadastrar(RegisterRequest req) {
        if (usuarios.findByLogin(req.login()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login já existe");
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(req.login());
        usuario.setSenha(encoder.encode(req.senha()));
        usuario.setPerfil(req.perfil());
        usuarios.save(usuario);
    }

    public LoginResponse entrar(LoginRequest req) {
        Usuario usuario = usuarios.findByLogin(req.login())
            .filter(u -> encoder.matches(req.senha(), u.getSenha()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login ou senha inválidos"));
        return new LoginResponse(jwtService.gerar(usuario), usuario.getLogin(), usuario.getPerfil().name());
    }
}