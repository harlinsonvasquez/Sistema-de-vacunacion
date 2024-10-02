package com.sistema_de_vacunacion.sistemaVacunacion.api.controllers;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.VaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.VaccineResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IVaccineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vaccines")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class VaccineController {

    private final IVaccineService vaccineService;

    @PostMapping
    public VaccineResponse createVaccine(@RequestBody @Valid VaccineRequest request) {
        return vaccineService.create(request);
    }

    @PutMapping("/{id}")
    public VaccineResponse updateVaccine(@PathVariable Long id, @RequestBody  @Valid VaccineRequest request) {
        return vaccineService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData> deleteVaccine(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.deleteVaccine(id));
    }

    @GetMapping
    public Page<VaccineResponse> getAllVaccines(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return vaccineService.getAll(page, size);
    }
}
