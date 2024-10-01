package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.MunicipalityRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.MunicipalityResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Department;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Municipality;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IMunicipalityService;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.DepartmentRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.MunicipalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MunicipalityServiceImpl implements IMunicipalityService {
    private final MunicipalityRepository municipalityRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public MunicipalityResponse create(MunicipalityRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Municipality municipality = new Municipality();
        municipality.setName(request.getName());
        municipality.setDepartment(department);

        Municipality savedMunicipality = municipalityRepository.save(municipality);
        return convertToMunicipalityResponse(savedMunicipality);
    }

    @Override
    public MunicipalityResponse update(Long id, MunicipalityRequest request) {
        Municipality municipality = municipalityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Municipality not found"));
        municipality.setName(request.getName());
        Municipality updatedMunicipality = municipalityRepository.save(municipality);
        return convertToMunicipalityResponse(updatedMunicipality);
    }

    @Override
    public void delete(Long id) {
        municipalityRepository.deleteById(id);
    }

    @Override
    public Page<MunicipalityResponse> getAll(int page, int size) {
        return municipalityRepository.findAll(PageRequest.of(page, size))
                .map(this::convertToMunicipalityResponse);
    }

    @Override
    public List<MunicipalityResponse> getMunicipalitiesByDepartment(Long departmentId) {
        List<Municipality> municipalities = municipalityRepository.findByDepartmentId(departmentId);
        return municipalities.stream().map(this::convertToMunicipalityResponse).collect(Collectors.toList());
    }

    private MunicipalityResponse convertToMunicipalityResponse(Municipality municipality) {
        MunicipalityResponse response = new MunicipalityResponse();
        response.setId(municipality.getId());
        response.setName(municipality.getName());
        return response;
    }
}
