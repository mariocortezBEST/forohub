package com.alura.forohub.dto.response;

import java.time.LocalDateTime;

public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        String autorNombre,
        String cursoNombre
) {
}
