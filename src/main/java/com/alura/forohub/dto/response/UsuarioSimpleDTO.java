package com.alura.forohub.dto.response;

import com.alura.forohub.entity.Usuario;

public record UsuarioSimpleDTO(
        String nombre
) {
    public UsuarioSimpleDTO(Usuario autor) {
        this(autor.getNombre());
    }
}