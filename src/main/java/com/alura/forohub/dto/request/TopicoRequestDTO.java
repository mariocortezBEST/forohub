package com.alura.forohub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoRequestDTO(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Long cursoId
) {
}