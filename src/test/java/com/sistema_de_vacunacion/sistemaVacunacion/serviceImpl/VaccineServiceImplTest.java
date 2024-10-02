package com.sistema_de_vacunacion.sistemaVacunacion.serviceImpl;

import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.VaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.VaccineResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Vaccine;
import com.sistema_de_vacunacion.sistemaVacunacion.domain.exceptions.ResourceNotFoundException;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories.VaccineRepository;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.serviceImpl.VaccineServiceImpl;
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
import static org.mockito.Mockito.*;

class VaccineServiceImplTest {

    @Mock
    private VaccineRepository vaccineRepository;

    @InjectMocks
    private VaccineServiceImpl vaccineService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVaccineSuccess() {
        VaccineRequest request = new VaccineRequest("BCG", 5);
        Vaccine vaccine = new Vaccine(1L, "BCG", 5, null);

        when(vaccineRepository.save(any(Vaccine.class))).thenReturn(vaccine);

        VaccineResponse response = vaccineService.create(request);

        assertNotNull(response);
        assertEquals("BCG", response.getName());
        verify(vaccineRepository, times(1)).save(any(Vaccine.class));
    }

    @Test
    void testUpdateVaccineSuccess() {
        VaccineRequest request = new VaccineRequest("BCG", 5);
        Vaccine vaccine = new Vaccine(1L, "BCG", 5, null);

        when(vaccineRepository.findById(1L)).thenReturn(Optional.of(vaccine));
        when(vaccineRepository.save(any(Vaccine.class))).thenReturn(vaccine);

        VaccineResponse response = vaccineService.update(1L, request);

        assertNotNull(response);
        assertEquals("BCG", response.getName());
    }

    @Test
    void testDeleteVaccineSuccess() {
        Vaccine vaccine = new Vaccine(1L, "BCG", 5, null);
        when(vaccineRepository.findById(1L)).thenReturn(Optional.of(vaccine));

        vaccineService.deleteVaccine(1L);

        verify(vaccineRepository, times(1)).delete(vaccine);
    }

    @Test
    void testGetAllVaccines() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Vaccine vaccine = new Vaccine(1L, "BCG", 5, null);
        Page<Vaccine> vaccines = new PageImpl<>(Collections.singletonList(vaccine));

        when(vaccineRepository.findAll(pageRequest)).thenReturn(vaccines);

        Page<VaccineResponse> response = vaccineService.getAll(0, 10);

        assertEquals(1, response.getTotalElements());
    }
}