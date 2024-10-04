package com.sistema_de_vacunacion.sistemaVacunacion.api.controllers;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.MunicipalityRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.UpdateMunicipalityRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.MunicipalityResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IMunicipalityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/municipalities")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MunicipalityController {

    private final IMunicipalityService municipalityService;

    @PostMapping
    public MunicipalityResponse createMunicipality(@RequestBody @Valid MunicipalityRequest request) {
        return municipalityService.create(request);
    }

    @PutMapping("/{id}")
    public MunicipalityResponse updateMunicipality(@PathVariable Long id, @RequestBody @Valid UpdateMunicipalityRequest request) {
        return municipalityService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData> deleteMunicipality(@PathVariable Long id) {
        return ResponseEntity.ok(municipalityService.deleteMunicipality(id));
    }

    @GetMapping
    public Page<MunicipalityResponse> getAllMunicipalities(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        if (page < 0) {
            page = 0;
        }
        return municipalityService.getAll(page, size);
    }

    @GetMapping("/department/{departmentId}")
    public List<MunicipalityResponse> getMunicipalitiesByDepartment(@PathVariable Long departmentId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        if (page < 0) {
            page = 0;
        }
        return municipalityService.getMunicipalitiesByDepartment(departmentId);
    }
}
