package com.alura.forohub.infra.security;

import com.alura.forohub.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("ForoHub API")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new IllegalArgumentException("El token no puede ser nulo.");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("ForoHub API")
                    .build()
                    .verify(token);
            return verifier.getSubject();
        } catch (JWTVerificationException exception) {
            // El token no es válido (firma incorrecta, expirado, etc.)
            return null;
        }
    }

    private Instant generarFechaExpiracion() {
        return Instant.now().plusSeconds(jwtExpiration);
    }
}