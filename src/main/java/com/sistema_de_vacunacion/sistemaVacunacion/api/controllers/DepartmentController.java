package com.sistema_de_vacunacion.sistemaVacunacion.api.controllers;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.DepartmentRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.DepartmentResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IDepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

    private final IDepartmentService departmentService;


    @PostMapping
    public DepartmentResponse createDepartment(@RequestBody @Valid DepartmentRequest request) {
        return departmentService.create(request);
    }

    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentRequest request) {
        return departmentService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData> deleteDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }

    @GetMapping
    public Page<DepartmentResponse> getAllDepartments(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return departmentService.getAll(page, size);
    }
}
