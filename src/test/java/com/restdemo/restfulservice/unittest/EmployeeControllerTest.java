package com.restdemo.restfulservice.unittest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getAllEmployeesShouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getEmployeeByIdPresentShouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/employees/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getEmployeeByIdAbsentShouldReturnInternalServerError() throws Exception {
        mockMvc.perform(get("/employees/4999"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void postEmployeeShouldReturnStatusCreated() throws Exception {
        String requestBody = "{ \"name\": \"Smaug\", \"role\": \"Dragon\" }";
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Smaug"))
                .andExpect(jsonPath("$.role").value("Dragon"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void deleteEmployeePresentShouldReturnStatusOk() throws Exception {
        mockMvc.perform(delete("/employees/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void deleteEmployeeAbsentShouldReturnInternalServerError() throws Exception {
        mockMvc.perform(delete("/employees/100093"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
