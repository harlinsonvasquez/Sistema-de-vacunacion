package com.sistema_de_vacunacion.sistemaVacunacion.serviceImpl;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ApplyVaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ChildRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildBasicResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Child;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Municipality;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Vaccine;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.ResourceNotFoundException;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.ChildRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.MunicipalityRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.VaccineRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl.ChildServiceImpl;
import com.sistema_de_vacunacion.sistemaVacunacion.utils.enums.ErrorMessagesEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChildServiceImplTest {

    @Mock
    private ChildRepository childRepository;

    @Mock
    private VaccineRepository vaccineRepository;

    @Mock
    private MunicipalityRepository municipalityRepository;

    @InjectMocks
    private ChildServiceImpl childService;

    public ChildServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateChildSuccess() {
        ChildRequest request = new ChildRequest("Juan", LocalDate.of(2015, 5, 20), 1L);
        Municipality municipality = new Municipality(1L, "Municipio Test", null, null);

        when(municipalityRepository.findById(1L)).thenReturn(Optional.of(municipality));
        when(childRepository.save(any(Child.class))).thenAnswer(invocation -> {
            Child child = invocation.getArgument(0);
            child.setId(1L);
            return child;
        });

        ChildBasicResponse response = childService.create(request);

        assertNotNull(response);
        assertEquals("Juan", response.getName());
        assertEquals("Municipio Test", response.getMunicipalityName());
        verify(childRepository, times(1)).save(any(Child.class));
    }

    @Test
    void testCreateChildMunicipalityNotFound() {
        ChildRequest request = new ChildRequest("Juan", LocalDate.of(2015, 5, 20), 1L);
        when(municipalityRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> childService.create(request));

        assertEquals(ErrorMessagesEnum.MUNICIPALITY_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void testApplyVaccineSuccess() {

        Municipality municipality = new Municipality();
        municipality.setId(1L);
        municipality.setName("Municipio Test");

        Child child = new Child();
        child.setId(1L);
        child.setName("Juan");
        child.setBirthDate(LocalDate.of(2022, 5, 10));
        child.setMunicipality(municipality);
        child.setVaccines(new ArrayList<>());

        Vaccine vaccine = new Vaccine();
        vaccine.setId(1L);
        vaccine.setName("BCG");

        vaccine.setMaxAge(30);

        when(childRepository.findById(1L)).thenReturn(Optional.of(child));
        when(vaccineRepository.findById(1L)).thenReturn(Optional.of(vaccine));
        when(childRepository.existsByChildIdAndVaccineId(1L, 1L)).thenReturn(false);

        ChildResponse response = childService.applyVaccineToChild(new ApplyVaccineRequest(1L, 1L));

        assertNotNull(response);
        assertEquals(1, response.getVaccines().size());
    }

    @Test
    void testApplyVaccineChildNotFound() {
        ApplyVaccineRequest request = new ApplyVaccineRequest(1L, 2L);
        when(childRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> childService.applyVaccineToChild(request));

        assertEquals(ErrorMessagesEnum.CHILD_NOT_FOUND.getMessage(), exception.getMessage());
    }
}