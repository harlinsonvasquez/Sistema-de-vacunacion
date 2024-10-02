package com.sistema_de_vacunacion.sistemaVacunacion.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema_de_vacunacion.sistemaVacunacion.api.controllers.DepartmentController;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.request.DepartmentRequest;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.DepartmentResponse;
import com.sistema_de_vacunacion.sistemaVacunacion.api.dtos.response.ResponseData;
import com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice.IDepartmentService;
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

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateDepartmentSuccess() throws Exception {
        DepartmentRequest request = new DepartmentRequest("Salud");
        DepartmentResponse response = new DepartmentResponse(1L, "Salud", null);

        when(departmentService.create(any(DepartmentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Salud"));
    }

    @Test
    void testUpdateDepartmentSuccess() throws Exception {
        DepartmentRequest request = new DepartmentRequest("Educación");
        DepartmentResponse response = new DepartmentResponse(1L, "Educación", null);

        when(departmentService.update(any(Long.class), any(DepartmentRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/departments/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Educación"));
    }

    @Test
    void testDeleteDepartmentSuccess() throws Exception {
        ResponseData responseData = new ResponseData("200", "OK", "Eliminado exitosamente");

        when(departmentService.deleteDepartment(any(Long.class))).thenReturn(responseData);

        mockMvc.perform(delete("/api/departments/delete/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado exitosamente"));
    }
}
