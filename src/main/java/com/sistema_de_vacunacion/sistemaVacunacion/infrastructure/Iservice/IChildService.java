package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ApplyVaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ChildRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildBasicResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.CustomValidationException;

import java.util.List;

public interface IChildService extends CrudService<ChildRequest, ChildBasicResponse,Long>{

    public List<ChildBasicResponse> getChildrenByMunicipality(Long municipalityId);

    public Double getAverageAgeByMunicipality(Long municipalityId);

    public List<ChildBasicResponse> getVaccinatedChildrenByMunicipality(Long municipalityId);

    public ChildResponse applyVaccineToChild(ApplyVaccineRequest request);

    ResponseData deleteChildren(Long id ) throws CustomValidationException;
}
