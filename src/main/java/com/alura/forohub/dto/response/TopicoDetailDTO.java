package com.alura.forohub.dto.response;

import com.alura.forohub.entity.Topico;
import com.alura.forohub.enums.StatusTopico;

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
    public TopicoDetailDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                new UsuarioSimpleDTO(topico.getAutor()),
                new CursoSimpleDTO(topico.getCurso())
        );
    }
}
