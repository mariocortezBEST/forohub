package com.alura.forohub.dto.response;

import com.alura.forohub.entity.Curso;

public record CursoSimpleDTO(
        String nombre
) {
    public CursoSimpleDTO(Curso curso) {
        this(curso.getNombre());
    }
}