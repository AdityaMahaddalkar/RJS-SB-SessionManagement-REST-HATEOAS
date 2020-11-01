package com.restdemo.restfulservice.unittest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllEmployeesShouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"));
    }

    @Test
    public void getEmployeeByIdPresentShouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/employees/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void getEmployeeByIdAbsentShouldReturnInternalServerError() throws Exception {
        mockMvc.perform(get("/employees/4999"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void postEmployeeShouldReturnStatusCreated() throws Exception {
        String requestBody = "{ \"name\": \"Smaug\", \"role\": \"Dragon\" }";
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Smaug"))
                .andExpect(jsonPath("$.role").value("Dragon"));
    }

    @Test
    public void deleteEmployeePresentShouldReturnStatusOk() throws Exception {
        mockMvc.perform(delete("/employees/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeAbsentShouldReturnInternalServerError() throws Exception {
        mockMvc.perform(delete("/employees/100093"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
