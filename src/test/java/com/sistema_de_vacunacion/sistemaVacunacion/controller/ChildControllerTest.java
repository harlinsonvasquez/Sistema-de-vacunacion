package com.sistema_de_vacunacion.sistemaVacunacion.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema_de_vacunacion.sistemaVacunacion.api.controllers.ChildController;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ApplyVaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.ChildRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildBasicResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ChildResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IChildService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChildController.class)
public class ChildControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IChildService childService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateChildSuccess() throws Exception {
        ChildRequest request = new ChildRequest("Juan", LocalDate.of(2015, 5, 20), 1L);
        ChildBasicResponse response = new ChildBasicResponse(1L, "Juan", LocalDate.of(2015, 5, 20), "Municipio Test");

        when(childService.create(any(ChildRequest.class))).thenReturn(response);

        mockMvc.perform(post("/children/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan"));
    }

    @Test
    void testApplyVaccineSuccess() throws Exception {
        ApplyVaccineRequest request = new ApplyVaccineRequest(1L, 2L);
        ChildResponse response = new ChildResponse(1L, "Juan", LocalDate.of(2015, 5, 20), "Municipio Test", null);

        when(childService.applyVaccineToChild(any(ApplyVaccineRequest.class))).thenReturn(response);

        mockMvc.perform(post("/children/applyVaccine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan"));
    }
}
