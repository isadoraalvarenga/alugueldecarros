package com.aluguel.carros.security;

import com.aluguel.carros.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private static final long EXPIRACAO_MS = 1000L * 60 * 60 * 8;
    private final SecretKey chave;

    public JwtService(@Value("${jwt.secret}") String segredo) {
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));
    }

    public String gerar(Usuario usuario) {
        Date agora = new Date();
        return Jwts.builder()
            .subject(usuario.getLogin())
            .claim("perfil", usuario.getPerfil().name())
            .issuedAt(agora)
            .expiration(new Date(agora.getTime() + EXPIRACAO_MS))
            .signWith(chave)
            .compact();
    }

    public Claims ler(String token) {
        return Jwts.parser().verifyWith(chave).build().parseSignedClaims(token).getPayload();
    }
}