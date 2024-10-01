package com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineRequest {
    @NotBlank(message = "Vaccine name cannot be blank")
    @Size(max = 100, message = "Vaccine name must be at most 100 characters")
    private String name;

    @Min(value = 0, message = "Maximum age must be at least 0")
    @NotNull(message = "Maximum age is required")
    private Integer maxAge;

}
