package com.sistema_de_vacunacion.sistemaVacunacion.utils.enums;

public enum SuccessMessagesEnum {
    SUCCESSFULLY_CREATED("Creado exitosamente"),
    SUCCESSFULLY_DELETED("Eliminado exitosamente"),
    SUCCESSFULLY_CONFIRMED("Vacunado exitosamente"),
    STATUS_OK("OK");


    private final String message;

    SuccessMessagesEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
