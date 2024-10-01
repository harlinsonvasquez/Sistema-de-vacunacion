package com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseData {
    private String code;
    private String status;
    private String message;

}
