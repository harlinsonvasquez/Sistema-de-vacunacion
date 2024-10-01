package com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildBasicResponse {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String municipalityName;
}
