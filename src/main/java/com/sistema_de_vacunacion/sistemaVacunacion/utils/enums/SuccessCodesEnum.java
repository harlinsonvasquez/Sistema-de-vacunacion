package com.sistema_de_vacunacion.sistemaVacunacion.utils.enums;

public enum SuccessCodesEnum {
    SUCCESS_CODE("200");

    private final String code;

    SuccessCodesEnum(String code) {
        this.code = code;
    }

    public String getMessage() {
        return code;
    }
}
