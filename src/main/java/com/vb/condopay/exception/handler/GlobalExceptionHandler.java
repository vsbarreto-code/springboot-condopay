package com.vb.condopay.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<String> handlerConflitoException(ConflitoException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(exception.getMessage());
    }

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<String> handlerNaoEncontradoException(NaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(exception.getMessage());
    }


}
