package com.alura.forohub.dto.response;

public record TokenResponseDTO(
        String token,
        String tipo,
        Long expiracion
) {
}
