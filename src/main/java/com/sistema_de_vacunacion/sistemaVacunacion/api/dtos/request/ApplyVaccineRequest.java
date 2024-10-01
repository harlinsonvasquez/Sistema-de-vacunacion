package com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyVaccineRequest {
    @NotNull(message = "Child ID is required")
    private Long childId;

    @NotNull(message = "Vaccine ID is required")
    private Long vaccineId;
}
