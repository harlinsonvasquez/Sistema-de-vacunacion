package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.MunicipalityRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.UpdateMunicipalityRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.MunicipalityResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Child;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Department;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Municipality;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.CustomValidationException;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.ResourceNotFoundException;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IMunicipalityService;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.DepartmentRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.MunicipalityRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.ErrorMessagesEnum;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.SuccessCodesEnum;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.SuccessMessagesEnum;
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
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.DEPARTMENT_NOT_FOUND.getMessage()));

        Municipality municipality = new Municipality();
        municipality.setName(request.getName());
        municipality.setDepartment(department);

        Municipality savedMunicipality = municipalityRepository.save(municipality);
        return convertToMunicipalityResponse(savedMunicipality);
    }

    @Override
    public MunicipalityResponse update(Long aLong, MunicipalityRequest request) {
        return null;
    }

    @Override
    public MunicipalityResponse update(Long id, UpdateMunicipalityRequest request) {
        Municipality municipality = municipalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.MUNICIPALITY_NOT_FOUND.getMessage()));

        municipality.setName(request.getName());

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.DEPARTMENT_NOT_FOUND.getMessage()));
            municipality.setDepartment(department);
        }

        Municipality updatedMunicipality = municipalityRepository.save(municipality);
        return convertToMunicipalityResponse(updatedMunicipality);
    }

    @Override
    public ResponseData deleteMunicipality(Long id) throws CustomValidationException {
        Municipality municipality = municipalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.MUNICIPALITY_NOT_FOUND.getMessage()));

        municipalityRepository.delete(municipality);

        return new ResponseData(
                SuccessCodesEnum.SUCCESS_CODE.getMessage(),
                SuccessMessagesEnum.STATUS_OK.getMessage(),
                SuccessMessagesEnum.SUCCESSFULLY_DELETED.getMessage()
        );
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
