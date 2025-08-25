package com.alura.forohub.service;

import com.alura.forohub.entity.Curso;
import com.alura.forohub.entity.Topico;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TopicoSpecification {

    public static Specification<Topico> conFiltros(String nombreCurso, Integer anio) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombreCurso != null && !nombreCurso.isEmpty()) {
                Join<Topico, Curso> cursoJoin = root.join("curso");
                predicates.add(criteriaBuilder.equal(cursoJoin.get("nombre"), nombreCurso));
            }
            if (anio != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("fechaCreacion")), anio));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}