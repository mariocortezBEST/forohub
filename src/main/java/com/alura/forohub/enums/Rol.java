package com.alura.forohub.enums;



public enum Rol {
    ESTUDIANTE("Estudiante"),
    PROFESOR("Profesor"),
    MODERADOR("Moderador"),
    ADMIN("Administrador");

    private final String descripcion;

    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
