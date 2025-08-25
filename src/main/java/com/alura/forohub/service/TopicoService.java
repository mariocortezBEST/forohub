package com.alura.forohub.service;

import com.alura.forohub.dto.request.TopicoRequestDTO;
import com.alura.forohub.dto.request.TopicoUpdateDTO;
import com.alura.forohub.dto.response.TopicoDetailDTO;
import com.alura.forohub.dto.response.TopicoResponseDTO;
import com.alura.forohub.entity.Topico;
import com.alura.forohub.enums.StatusTopico;
import com.alura.forohub.exception.DuplicateTopicoException;
import com.alura.forohub.exception.TopicoNotFoundException;
import com.alura.forohub.repository.CursoRepository;
import com.alura.forohub.repository.TopicoRepository;
import com.alura.forohub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }
    
    @Transactional
    public TopicoResponseDTO crear(TopicoRequestDTO dto, String emailAutor) {
        // Validar duplicados
        validarTopicoDuplicado(dto.titulo(), dto.mensaje());

        var autor = usuarioRepository.findByEmail(emailAutor)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        var curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        var topico = new Topico();
        topico.setTitulo(dto.titulo());
        topico.setMensaje(dto.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setStatus(StatusTopico.ABIERTO);

        var topicoGuardado = topicoRepository.save(topico);
        return new TopicoResponseDTO(topicoGuardado);
    }

    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> listar(Pageable paginacion, String curso, Integer año) {
        var specification = TopicoSpecification.conFiltros(curso, año);
        return topicoRepository.findAll(specification, paginacion)
                .map(TopicoResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public TopicoDetailDTO obtenerPorId(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicoNotFoundException("Tópico con id " + id + " no encontrado."));
        return new TopicoDetailDTO(topico);
    }

    @Transactional
    public TopicoResponseDTO actualizar(Long id, TopicoUpdateDTO dto, String emailAutor) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicoNotFoundException("Tópico con id " + id + " no encontrado."));

        if (!topico.getAutor().getEmail().equals(emailAutor)) {
            throw new AccessDeniedException("No tienes permiso para actualizar este tópico.");
        }

        if (dto.titulo() != null && !dto.titulo().isBlank()) topico.setTitulo(dto.titulo());
        if (dto.mensaje() != null && !dto.mensaje().isBlank()) topico.setMensaje(dto.mensaje());

        return new TopicoResponseDTO(topico);
    }

    @Transactional
    public void eliminar(Long id, String emailAutor) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicoNotFoundException("Tópico con id " + id + " no encontrado."));

        if (!topico.getAutor().getEmail().equals(emailAutor)) {
            throw new AccessDeniedException("No tienes permiso para eliminar este tópico.");
        }
        topicoRepository.delete(topico);
    }

    private void validarTopicoDuplicado(String titulo, String mensaje) {
        if (topicoRepository.existsByTituloAndMensaje(titulo, mensaje)) {
            throw new DuplicateTopicoException("Ya existe un tópico con el mismo título y mensaje");
        }
    }
}