package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.VaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.VaccineResponse;

public interface IVaccineService extends CrudService<VaccineRequest, VaccineResponse, Long> {
}
