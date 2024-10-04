package com.sistema_de_vacunacion.sistemaVacunacion.api.controllers;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ApplyVaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ChildRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildBasicResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/children")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ChildController {
    private final IChildService childService;


    @PostMapping(path = "/create")
    public ChildBasicResponse createChild(@RequestBody @Valid ChildRequest request) {
        return childService.create(request);
    }

    @PutMapping("/update/{id}")
    public ChildBasicResponse updateChild(@PathVariable Long id, @RequestBody ChildRequest request) {
        return childService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData> deleteChild(@PathVariable Long id) {
        return ResponseEntity.ok(childService.deleteChildren(id));
    }

    @GetMapping(path = "/getAllChildren")
    public Page<ChildBasicResponse> getAllChildren(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        if (page < 0) {
            page = 0;
        }
        return childService.getAll(page, size);
    }

    @GetMapping("/municipality/{municipalityId}")
    public List<ChildBasicResponse> getChildrenByMunicipality(@PathVariable Long municipalityId) {
        return childService.getChildrenByMunicipality(municipalityId);
    }

    @GetMapping("/municipality/{municipalityId}/average-age")
    public Double getAverageAgeByMunicipality(@PathVariable Long municipalityId) {
        return childService.getAverageAgeByMunicipality(municipalityId);
    }

    @GetMapping("/municipality/{municipalityId}/vaccinated")
    public List<ChildBasicResponse> getVaccinatedChildrenByMunicipality(@PathVariable Long municipalityId) {
        return childService.getVaccinatedChildrenByMunicipality(municipalityId);
    }

    @PostMapping(path = "/applyVaccine")
    public ResponseEntity<ChildResponse> applyVaccine(@RequestBody @Valid ApplyVaccineRequest request) {
        return ResponseEntity.ok(childService.applyVaccineToChild(request));
    }
}
