package com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMunicipalityRequest {
    @NotBlank(message = "Municipality name cannot be blank")
    @Size(max = 100, message = "Municipality name must be at most 100 characters")
    private String name;

    private Long departmentId;
}
