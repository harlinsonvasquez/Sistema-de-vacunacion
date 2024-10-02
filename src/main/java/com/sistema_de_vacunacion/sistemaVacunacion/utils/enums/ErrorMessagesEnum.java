package com.sistema_de_vacunacion.sistemaVacunacion.utils.enums;

public enum ErrorMessagesEnum {

    CHILD_NOT_FOUND("Niño no encontrado"),
    MUNICIPALITY_NOT_FOUND("Municipio no encontrado"),
    DEPARTMENT_NOT_FOUND("Departamento no encontrado"),
    VACCINE_NOT_FOUND("Vacuna no encontrada"),
    AGE_NOT_PERMITE("Restriccion de edad no se puede aplicar esta vacuna al niño"),
    VACCINE_ALREADY_EXISTS("No se puede aplicar la vacuna porque el niño ya la tiene aplicada"),
    FIELD_REQUIRED("Este campo es obligatorio");

    private final String message;

    ErrorMessagesEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
