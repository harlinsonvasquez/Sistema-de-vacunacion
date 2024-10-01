package com.sistema_de_vacunacion.sistemaVacunacion.api.controllers;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.VaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.VaccineResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IVaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vaccines")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class VaccineController {

    private final IVaccineService vaccineService;

    @PostMapping
    public VaccineResponse createVaccine(@RequestBody VaccineRequest request) {
        return vaccineService.create(request);
    }

    // Actualizar una vacuna existente
    @PutMapping("/{id}")
    public VaccineResponse updateVaccine(@PathVariable Long id, @RequestBody VaccineRequest request) {
        return vaccineService.update(id, request);
    }

    // Eliminar una vacuna por ID
    @DeleteMapping("/{id}")
    public void deleteVaccine(@PathVariable Long id) {
        vaccineService.delete(id);
    }

    // Obtener todas las vacunas con paginación
    @GetMapping
    public Page<VaccineResponse> getAllVaccines(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return vaccineService.getAll(page, size);
    }
}
