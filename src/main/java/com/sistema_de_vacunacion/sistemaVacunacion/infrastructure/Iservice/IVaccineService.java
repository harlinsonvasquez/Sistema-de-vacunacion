package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.VaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.VaccineResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.CustomValidationException;

public interface IVaccineService extends CrudService<VaccineRequest, VaccineResponse, Long> {
    ResponseData deleteVaccine(Long id ) throws CustomValidationException;
}
