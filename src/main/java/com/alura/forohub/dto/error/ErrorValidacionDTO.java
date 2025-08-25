package com.alura.forohub.dto.error;

import org.springframework.validation.FieldError;

public record ErrorValidacionDTO(String campo, String mensaje) {
    public ErrorValidacionDTO(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}