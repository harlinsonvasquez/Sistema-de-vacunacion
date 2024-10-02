package com.sistema_de_vacunacion.sistemaVacunacion.serviceImpl;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.MunicipalityRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.MunicipalityResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Department;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Municipality;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.ResourceNotFoundException;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.DepartmentRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.MunicipalityRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl.MunicipalityServiceImpl;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class MunicipalityServiceImplTest {
    @Mock
    private MunicipalityRepository municipalityRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private MunicipalityServiceImpl municipalityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMunicipalitySuccess() {
        MunicipalityRequest request = new MunicipalityRequest("Municipio Test", 1L);
        Department department = new Department(1L, "Salud", null);
        Municipality municipality = new Municipality(1L, "Municipio Test", department, null);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(municipalityRepository.save(any(Municipality.class))).thenReturn(municipality);

        MunicipalityResponse response = municipalityService.create(request);

        assertNotNull(response);
        assertEquals("Municipio Test", response.getName());
    }

    @Test
    void testUpdateMunicipalitySuccess() {
        MunicipalityRequest request = new MunicipalityRequest("Nuevo Nombre", 1L);
        Municipality municipality = new Municipality(1L, "Municipio Test", null, null);

        when(municipalityRepository.findById(1L)).thenReturn(Optional.of(municipality));
        when(municipalityRepository.save(any(Municipality.class))).thenReturn(municipality);

        MunicipalityResponse response = municipalityService.update(1L, request);

        assertNotNull(response);
        assertEquals("Nuevo Nombre", response.getName());
    }

    @Test
    void testDeleteMunicipalitySuccess() {
        Municipality municipality = new Municipality(1L, "Municipio Test", null, null);
        when(municipalityRepository.findById(1L)).thenReturn(Optional.of(municipality));

        municipalityService.deleteMunicipality(1L);

        verify(municipalityRepository, times(1)).delete(municipality);
    }

    @Test
    void testGetAllMunicipalities() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Municipality municipality = new Municipality(1L, "Municipio Test", null, null);
        Page<Municipality> municipalities = new PageImpl<>(Collections.singletonList(municipality));

        when(municipalityRepository.findAll(pageRequest)).thenReturn(municipalities);

        Page<MunicipalityResponse> response = municipalityService.getAll(0, 10);

        assertEquals(1, response.getTotalElements());
    }

    @Test
    void testGetMunicipalitiesByDepartmentSuccess() {
        Municipality municipality = new Municipality(1L, "Municipio Test", null, null);
        when(municipalityRepository.findByDepartmentId(1L)).thenReturn(List.of(municipality));

        List<MunicipalityResponse> response = municipalityService.getMunicipalitiesByDepartment(1L);

        assertNotNull(response);
        assertEquals(1, response.size());
    }
}
