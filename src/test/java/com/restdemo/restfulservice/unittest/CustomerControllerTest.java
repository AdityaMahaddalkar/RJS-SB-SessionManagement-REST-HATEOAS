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
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getAllCustomersShouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getCustomerByIdPresentShouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/customers/11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(11));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getCustomersByIdMinus5ShouldInternalServerError() throws Exception {
        mockMvc.perform(get("/customers/-5"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void postCustomersShouldReturnStatusCreated() throws Exception {
        String requestBody = "{ \"firstName\": \"Rosa\", \"lastName\": \"Diaz\" }";
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Rosa"))
                .andExpect(jsonPath("$.lastName").value("Diaz"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void postInvalidCustomerBodyShouldReturnStatusCreated() throws Exception {
        String requestBody = "{ \"hypertext\": \"Car thief\", \"firstName\": \"Doug\" }";
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Doug"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void deleteCustomerShouldReturnStatusOkIfCustomerExists() throws Exception {
        mockMvc.perform(delete("/customers/12"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
