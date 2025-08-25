package com.alura.forohub.dto.response;

import com.alura.forohub.entity.Topico;

import java.time.LocalDateTime;

public record TopicoResponseDTO(
        Long id, String titulo, String mensaje, LocalDateTime fechaCreacion
) {
    public TopicoResponseDTO(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion());
    }
}