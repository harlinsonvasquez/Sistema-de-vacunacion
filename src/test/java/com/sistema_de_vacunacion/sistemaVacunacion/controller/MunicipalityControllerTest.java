package com.sistema_de_vacunacion.sistemaVacunacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema_de_vacunacion.sistemaVacunacion.api.controllers.MunicipalityController;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.MunicipalityRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.MunicipalityResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IMunicipalityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MunicipalityController.class)
class MunicipalityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMunicipalityService municipalityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateMunicipalitySuccess() throws Exception {
        MunicipalityRequest request = new MunicipalityRequest("Municipio Test", 1L);
        MunicipalityResponse response = new MunicipalityResponse(1L, "Municipio Test", "Departamento Test");

        when(municipalityService.create(any(MunicipalityRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/municipalities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Municipio Test"));
    }

    @Test
    void testUpdateMunicipalitySuccess() throws Exception {
        MunicipalityRequest request = new MunicipalityRequest("Nuevo Nombre", 1L);
        MunicipalityResponse response = new MunicipalityResponse(1L, "Nuevo Nombre", "Departamento Test");

        when(municipalityService.update(any(Long.class), any(MunicipalityRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/municipalities/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nuevo Nombre"));
    }

    @Test
    void testDeleteMunicipalitySuccess() throws Exception {
        ResponseData responseData = new ResponseData("200", "OK", "Eliminado exitosamente");

        when(municipalityService.deleteMunicipality(any(Long.class))).thenReturn(responseData);

        mockMvc.perform(delete("/api/municipalities/delete/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado exitosamente"));
    }
}
