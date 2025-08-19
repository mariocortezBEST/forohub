package com.alura.forohub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TopicoRequestDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(min = 5, max = 200, message = "El título debe tener entre 5 y 200 caracteres")
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        @Size(min = 10, message = "El mensaje debe tener al menos 10 caracteres")
        String mensaje,

        @NotNull(message = "El ID del curso es obligatorio")
        Long cursoId) {
}
