package com.alura.forohub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidacionDTO>> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors().stream()
                .map(error -> new ErrorValidacionDTO(error.getField(), error.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(TopicoNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleTopicoNotFound(TopicoNotFoundException ex) {
        var error = new ErrorDTO("Tópico no encontrado", ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DuplicateTopicoException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateTopico(DuplicateTopicoException ex) {
        var error = new ErrorDTO("Tópico duplicado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}