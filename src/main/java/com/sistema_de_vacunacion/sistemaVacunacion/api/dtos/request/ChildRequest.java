package com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildRequest {

    @NotBlank(message = "Child's name cannot be blank")
    @Size(max = 100, message = "Child's name must be at most 100 characters")
    private String name;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotNull(message = "Municipality ID is required")
    private Long municipalityId;
}
