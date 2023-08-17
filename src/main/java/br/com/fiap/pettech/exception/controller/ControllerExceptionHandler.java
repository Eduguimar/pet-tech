package br.com.fiap.pettech.exception.controller;

import br.com.fiap.pettech.exception.service.ControllerNotFoundException;
import br.com.fiap.pettech.exception.service.DefaultError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    private DefaultError error = new DefaultError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<DefaultError> entityNotFound(ControllerNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entity Not Found");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FormValidation> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        FormValidation FormValidation = new FormValidation();
        FormValidation.setTimestamp(Instant.now());
        FormValidation.setStatus(status.value());
        FormValidation.setError("Validation Error");
        FormValidation.setMessage(exception.getMessage());
        FormValidation.setPath(request.getRequestURI());

        for (FieldError field: exception.getBindingResult().getFieldErrors()) {
            FormValidation.addMessages(field.getField(), field.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(FormValidation);
    }
}
