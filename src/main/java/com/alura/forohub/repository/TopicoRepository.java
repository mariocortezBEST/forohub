package com.alura.forohub.repository;

import com.alura.forohub.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>, JpaSpecificationExecutor<Topico> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso ORDER BY t.fechaCreacion ASC")
    Page<Topico> findByCursoNombre(@Param("nombreCurso") String nombreCurso, Pageable pageable);

    @Query("SELECT t FROM Topico t WHERE YEAR(t.fechaCreacion) = :año ORDER BY t.fechaCreacion ASC")
    Page<Topico> findByAño(@Param("año") Integer año, Pageable pageable);
}