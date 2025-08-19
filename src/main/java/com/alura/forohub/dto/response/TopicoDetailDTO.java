package com.alura.forohub.dto.response;

import java.time.LocalDateTime;

public record TopicoDetailDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        UsuarioSimpleDTO autor,
        CursoSimpleDTO curso
) {
}
