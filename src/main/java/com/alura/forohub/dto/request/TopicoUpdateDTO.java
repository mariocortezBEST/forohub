package com.alura.forohub.dto.request;

public record TopicoUpdateDTO(
        String titulo,
        String mensaje,
        StatusTopico status) {
}
