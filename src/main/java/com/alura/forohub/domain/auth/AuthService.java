package com.alura.forohub.domain.auth;

import com.alura.forohub.entity.Usuario;
import com.alura.forohub.infra.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public TokenResponseDTO autenticar(LoginRequestDTO loginRequestDTO) {
        // 1. Crea un token de autenticación con el email (username) y password
        Authentication authToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password());
        // 2. Spring Security valida las credenciales
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        // 3. Si son válidas, genera el token JWT
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        // 4. Devuelve el token en un DTO
        return new TokenResponseDTO(jwtToken);
    }
}