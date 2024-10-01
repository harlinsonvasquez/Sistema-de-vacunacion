package com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChildResponse {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String municipalityName;
    private List<VaccineResponse> vaccines;
}
