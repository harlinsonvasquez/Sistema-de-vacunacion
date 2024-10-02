package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.DepartmentRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.DepartmentResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Child;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Department;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.CustomValidationException;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.ResourceNotFoundException;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IDepartmentService;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.DepartmentRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.ErrorMessagesEnum;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.SuccessCodesEnum;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.SuccessMessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements IDepartmentService {
    private final DepartmentRepository departmentRepository;


    @Override
    public DepartmentResponse create(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        Department savedDepartment = departmentRepository.save(department);
        return convertToDepartmentResponse(savedDepartment);
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.DEPARTMENT_NOT_FOUND.getMessage()));
        department.setName(request.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return convertToDepartmentResponse(updatedDepartment);
    }

    @Override
    public ResponseData deleteDepartment(Long id) throws CustomValidationException {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessagesEnum.DEPARTMENT_NOT_FOUND.getMessage()));

        departmentRepository.delete(department);

        return new ResponseData(
                SuccessCodesEnum.SUCCESS_CODE.getMessage(),
                SuccessMessagesEnum.STATUS_OK.getMessage(),
                SuccessMessagesEnum.SUCCESSFULLY_DELETED.getMessage()
        );
    }

    @Override
    public Page<DepartmentResponse> getAll(int page, int size) {
        return departmentRepository.findAll(PageRequest.of(page, size))
                .map(this::convertToDepartmentResponse);
    }

    private DepartmentResponse convertToDepartmentResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());
        return response;
    }


}
