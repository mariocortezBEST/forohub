package com.alura.forohub.exception;

import com.alura.forohub.dto.error.ErrorDTO;
import com.alura.forohub.dto.error.ErrorValidacionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidacionDTO>> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors().stream()
                .map(ErrorValidacionDTO::new) // Usamos el constructor que creamos, ¡más limpio!
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(TopicoNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleTopicoNotFound(TopicoNotFoundException ex) {
        // Corregimos para que el cuerpo de la respuesta no vaya vacío
        var error = new ErrorDTO("Recurso no encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateTopicoException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateTopico(DuplicateTopicoException ex) {
        var error = new ErrorDTO("Tópico duplicado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(EntityNotFoundException ex) {
        var error = new ErrorDTO("Recurso no encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handleAccessDenied(AccessDeniedException ex) {
        var error = new ErrorDTO("Acceso denegado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}