package com.alura.forohub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateTopicoException extends RuntimeException {
    public DuplicateTopicoException(String message) {
        super(message);
    }
}