package com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MunicipalityResponse {
    private Long id;
    private String name;
    private String departmentName;
}
