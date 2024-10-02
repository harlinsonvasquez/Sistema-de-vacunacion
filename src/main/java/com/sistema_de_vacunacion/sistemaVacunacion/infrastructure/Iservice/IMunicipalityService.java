package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.MunicipalityRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.MunicipalityResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.CustomValidationException;

import java.util.List;

public interface IMunicipalityService extends CrudService<MunicipalityRequest, MunicipalityResponse, Long> {

    public List<MunicipalityResponse> getMunicipalitiesByDepartment(Long departmentId);
    ResponseData deleteMunicipality(Long id ) throws CustomValidationException;
}
