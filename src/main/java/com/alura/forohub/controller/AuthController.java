package com.alura.forohub.controller;

import com.alura.forohub.domain.auth.AuthService;
import com.alura.forohub.domain.auth.LoginRequestDTO;
import com.alura.forohub.domain.auth.TokenResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        var token = authService.autenticar(dto);
        return ResponseEntity.ok(token);
    }

    // --- Endpoint Temporal para Debugging ---
    // Este endpoint debe ser eliminado en producción
  /*  @GetMapping("/hash/{password}")
    public String hashPassword(@PathVariable String password) {
        return "El hash para '" + password + "' es: " + passwordEncoder.encode(password);
    }*/ //esto se habilito temporalmente para solucionar inconveniente de acceso.
}