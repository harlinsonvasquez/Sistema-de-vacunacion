package com.sistema_de_vacunacion.sistemaVacunacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema_de_vacunacion.sistemaVacunacion.api.controllers.VaccineController;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.VaccineRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.VaccineResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IVaccineService;
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

@WebMvcTest(VaccineController.class)
class VaccineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVaccineService vaccineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateVaccineSuccess() throws Exception {
        VaccineRequest request = new VaccineRequest("BCG", 5);
        VaccineResponse response = new VaccineResponse(1L, "BCG", 5);

        when(vaccineService.create(any(VaccineRequest.class))).thenReturn(response);

        mockMvc.perform(post("/vaccines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BCG"));
    }

    @Test
    void testUpdateVaccineSuccess() throws Exception {
        VaccineRequest request = new VaccineRequest("BCG", 5);
        VaccineResponse response = new VaccineResponse(1L, "BCG", 5);

        when(vaccineService.update(any(Long.class), any(VaccineRequest.class))).thenReturn(response);

        mockMvc.perform(put("/vaccines/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BCG"));
    }

    @Test
    void testDeleteVaccineSuccess() throws Exception {
        ResponseData responseData = new ResponseData("200", "OK", "Eliminado exitosamente");

        when(vaccineService.deleteVaccine(any(Long.class))).thenReturn(responseData);

        mockMvc.perform(delete("/vaccines/delete/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado exitosamente"));
    }
}
