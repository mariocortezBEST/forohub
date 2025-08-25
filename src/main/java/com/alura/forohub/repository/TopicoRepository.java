package com.alura.forohub.repository;

import com.alura.forohub.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TopicoRepository extends JpaRepository<Topico, Long>, JpaSpecificationExecutor<Topico> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}