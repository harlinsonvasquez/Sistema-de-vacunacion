package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ApplyVaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ChildRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildBasicResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.VaccineResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Child;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Municipality;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Vaccine;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.CustomValidationException;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.GlobalExceptionHandler;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.ResourceNotFoundException;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IChildService;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.ChildRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.MunicipalityRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.VaccineRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.ErrorMessagesEnum;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.SuccessCodesEnum;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.SuccessMessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildServiceImpl  implements IChildService {
    private final ChildRepository childRepository;
    private final VaccineRepository vaccineRepository;
    private final MunicipalityRepository municipalityRepository;

    @Override
    public ChildBasicResponse create(ChildRequest request) {
        Municipality municipality = municipalityRepository.findById(request.getMunicipalityId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.MUNICIPALITY_NOT_FOUND.getMessage()));

        Child child = new Child();
        child.setName(request.getName());
        child.setBirthDate(request.getBirthDate());
        child.setMunicipality(municipality);

        Child savedChild = childRepository.save(child);

        return convertToChildBasicResponse(savedChild);
    }

    @Override
    public ChildBasicResponse update(Long id, ChildRequest request) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.CHILD_NOT_FOUND.getMessage()));

        child.setName(request.getName());
        child.setBirthDate(request.getBirthDate());

        Municipality municipality = municipalityRepository.findById(request.getMunicipalityId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.MUNICIPALITY_NOT_FOUND.getMessage()));

        child.setMunicipality(municipality);

        Child updatedChild = childRepository.save(child);
        return convertToChildBasicResponse(updatedChild);
    }

    @Override
    public ResponseData deleteChildren(Long id) throws CustomValidationException {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.CHILD_NOT_FOUND.getMessage()));

        childRepository.delete(child);

        return new ResponseData(
                SuccessCodesEnum.SUCCESS_CODE.getMessage(),
                SuccessMessagesEnum.STATUS_OK.getMessage(),
                SuccessMessagesEnum.SUCCESSFULLY_DELETED.getMessage()
        );
    }
    @Override
    public Page<ChildBasicResponse> getAll(int page, int size) {
        return childRepository.findAll(PageRequest.of(page, size))
                .map(this::convertToChildBasicResponse);
    }

    @Override
    public List<ChildBasicResponse> getChildrenByMunicipality(Long municipalityId) {
        List<Child> children = childRepository.findByMunicipalityId(municipalityId);
        return children.stream().map(this::convertToChildBasicResponse).collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeByMunicipality(Long municipalityId) {
        return childRepository.getAverageAgeByMunicipality(municipalityId);
    }

    @Override
    public List<ChildBasicResponse> getVaccinatedChildrenByMunicipality(Long municipalityId) {
        List<Child> vaccinatedChildren = childRepository.findVaccinatedChildrenByMunicipality(municipalityId);
        return vaccinatedChildren.stream().map(this::convertToChildBasicResponse).collect(Collectors.toList());
    }

    @Override
    public ChildResponse applyVaccineToChild(ApplyVaccineRequest request) {
        Child child = childRepository.findById(request.getChildId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.CHILD_NOT_FOUND.getMessage()));


        Vaccine vaccine = vaccineRepository.findById(request.getVaccineId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.VACCINE_NOT_FOUND.getMessage()));

        int childAge = LocalDate.now().getYear() - child.getBirthDate().getYear();

        if (childAge > vaccine.getMaxAge()) {
            throw new ResourceNotFoundException(ErrorMessagesEnum.AGE_NOT_PERMITE.getMessage());
        }


        if (!child.getVaccines().contains(vaccine)) {
            child.getVaccines().add(vaccine);
            childRepository.save(child);
        }

        return convertToChildResponse(child);
    }


    private ChildResponse convertToChildResponse(Child child) {
        List<VaccineResponse> vaccineResponses = child.getVaccines().stream()
                .map(this::convertToVaccineResponse)
                .collect(Collectors.toList());

        return new ChildResponse(
                child.getId(),
                child.getName(),
                child.getBirthDate(),
                child.getMunicipality().getName(),
                vaccineResponses
        );
    }
    private ChildBasicResponse convertToChildBasicResponse(Child child) {
        return new ChildBasicResponse(
                child.getId(),
                child.getName(),
                child.getBirthDate(),
                child.getMunicipality().getName()
        );
    }
    private VaccineResponse convertToVaccineResponse(Vaccine vaccine) {
        return new VaccineResponse(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getMaxAge()
        );
    }
}
