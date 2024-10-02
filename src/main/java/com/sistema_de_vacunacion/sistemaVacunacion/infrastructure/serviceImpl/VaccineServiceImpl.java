package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.VaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.VaccineResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Child;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Vaccine;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.CustomValidationException;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.ResourceNotFoundException;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IVaccineService;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.VaccineRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.ErrorMessagesEnum;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.SuccessCodesEnum;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.SuccessMessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VaccineServiceImpl implements IVaccineService {
    private final VaccineRepository vaccineRepository;

    @Override
    public VaccineResponse create(VaccineRequest request) {
        Vaccine vaccine = new Vaccine();
        vaccine.setName(request.getName());
        vaccine.setMaxAge(request.getMaxAge());
        Vaccine savedVaccine = vaccineRepository.save(vaccine);
        return convertToVaccineResponse(savedVaccine);
    }

    @Override
    public VaccineResponse update(Long id, VaccineRequest request) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.VACCINE_NOT_FOUND.getMessage()));
        vaccine.setName(request.getName());
        vaccine.setMaxAge(request.getMaxAge());
        Vaccine updatedVaccine = vaccineRepository.save(vaccine);
        return convertToVaccineResponse(updatedVaccine);
    }

    @Override
    public ResponseData deleteVaccine(Long id) throws CustomValidationException {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.VACCINE_NOT_FOUND.getMessage()));

        vaccineRepository.delete(vaccine);

        return new ResponseData(
                SuccessCodesEnum.SUCCESS_CODE.getMessage(),
                SuccessMessagesEnum.STATUS_OK.getMessage(),
                SuccessMessagesEnum.SUCCESSFULLY_DELETED.getMessage()
        );
    }

    @Override
    public Page<VaccineResponse> getAll(int page, int size) {
        return vaccineRepository.findAll(PageRequest.of(page, size))
                .map(this::convertToVaccineResponse);
    }

    private VaccineResponse convertToVaccineResponse(Vaccine vaccine) {
        VaccineResponse response = new VaccineResponse();
        response.setId(vaccine.getId());
        response.setName(vaccine.getName());
        response.setMaxAge(vaccine.getMaxAge());
        return response;
    }

}
