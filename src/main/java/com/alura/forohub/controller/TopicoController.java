package com.alura.forohub.controller;

import com.alura.forohub.dto.request.TopicoRequestDTO;
import com.alura.forohub.dto.request.TopicoUpdateDTO;
import com.alura.forohub.dto.response.TopicoDetailDTO;
import com.alura.forohub.dto.response.TopicoResponseDTO;
import com.alura.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> crearTopico(
            @RequestBody @Valid TopicoRequestDTO dto,
            Authentication authentication) {
        var topico = topicoService.crear(dto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC)
            Pageable paginacion,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer año) {
        var topicos = topicoService.listar(paginacion, curso, año);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetailDTO> obtenerTopico(@PathVariable Long id) {
        var topico = topicoService.obtenerPorId(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid TopicoUpdateDTO dto,
            Authentication authentication) {
        var topico = topicoService.actualizar(id, dto, authentication.getName());
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(
            @PathVariable Long id,
            Authentication authentication) {
        topicoService.eliminar(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}