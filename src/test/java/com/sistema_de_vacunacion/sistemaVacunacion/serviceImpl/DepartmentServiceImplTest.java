package com.sistema_de_vacunacion.sistemaVacunacion.serviceImpl;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.DepartmentRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.DepartmentResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Department;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.ResourceNotFoundException;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.DepartmentRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl.DepartmentServiceImpl;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.ErrorMessagesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDepartmentSuccess() {
        DepartmentRequest request = new DepartmentRequest("Salud");
        Department department = new Department(1L, "Salud", null);

        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentResponse response = departmentService.create(request);

        assertNotNull(response);
        assertEquals("Salud", response.getName());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testUpdateDepartmentSuccess() {
        DepartmentRequest request = new DepartmentRequest("Educación");
        Department department = new Department(1L, "Salud", null);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentResponse response = departmentService.update(1L, request);

        assertNotNull(response);
        assertEquals("Educación", response.getName());
    }

    @Test
    void testUpdateDepartmentNotFound() {
        DepartmentRequest request = new DepartmentRequest("Educación");

        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> departmentService.update(1L, request));

        assertEquals(ErrorMessagesEnum.DEPARTMENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void testDeleteDepartmentSuccess() {
        Department department = new Department(1L, "Salud", null);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        departmentService.deleteDepartment(1L);

        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    void testDeleteDepartmentNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> departmentService.deleteDepartment(1L));

        assertEquals(ErrorMessagesEnum.DEPARTMENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void testGetAllDepartments() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Department department = new Department(1L, "Salud", null);
        Page<Department> departments = new PageImpl<>(Collections.singletonList(department));

        when(departmentRepository.findAll(pageRequest)).thenReturn(departments);

        Page<DepartmentResponse> response = departmentService.getAll(0, 10);

        assertEquals(1, response.getTotalElements());
        verify(departmentRepository, times(1)).findAll(pageRequest);
    }
}
