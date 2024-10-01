package com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions;

import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.ErrorMessageDTO;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.ErrorMessagesEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(
                HttpStatus.NOT_FOUND,
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorMessageDTO> handleCustomValidationException(CustomValidationException ex) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(
                HttpStatus.BAD_REQUEST,
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(
                HttpStatus.BAD_REQUEST,
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                errors.toString()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> handleGlobalException(Exception ex) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
