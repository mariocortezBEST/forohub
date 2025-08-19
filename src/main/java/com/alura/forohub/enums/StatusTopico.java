package com.alura.forohub.enums;

public enum StatusTopico {
    ABIERTO("Abierto"),
    CERRADO("Cerrado"),
    RESUELTO("Resuelto"),
    PENDIENTE("Pendiente");

    private final String descripcion;

    StatusTopico(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}