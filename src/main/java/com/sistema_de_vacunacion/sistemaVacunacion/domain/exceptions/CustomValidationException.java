package com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions;

public class CustomValidationException extends RuntimeException {
    public CustomValidationException(String message) {
        super(message);
    }
}
