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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(roles = "USER")
    @Test
    public void getAllOrdersReturnsStatusOk() throws Exception {
        mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void getOrderByIdPresentReturnsStatusOkAndOrderObject() throws Exception {
        mockMvc.perform(get("/orders/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void getOrderByIdAbsentReturnsStatusInternalServerError() throws Exception {
        mockMvc.perform(get("/orders/-100"))
                .andExpect(status().isInternalServerError());
    }

    @WithMockUser(roles = "USER")
    @Test
    void postNewOrderReturnsStatusOkAndOrderObject() throws Exception {
        String requestBody = "{ \"description\": \"Oranges\" }";
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Oranges"));
    }

    @WithMockUser(roles = "USER")
    @Test
    void putStatusCompleteOfOrderPresentReturnsStatusOk() throws Exception {
        mockMvc.perform(put("/orders/5/complete"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @WithMockUser(roles = "USER")
    @Test
    void putStatusCompleteOfOrderAbsentReturnsInternalServerError() throws Exception {
        mockMvc.perform(put("/orders/-100/complete"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @WithMockUser(roles = "USER")
    @Test
    void putStatusCompleteOfOrderCompletedOrCancelledReturnsMethodNotAllowed() throws Exception {
        mockMvc.perform(put("/orders/7/complete"))
                .andDo(print());
        mockMvc.perform(put("/orders/7/complete"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @WithMockUser(roles = "USER")
    @Test
    void deleteStatusCancelOfOrderPresentReturnsStatusOk() throws Exception {
        mockMvc.perform(delete("/orders/6/delete"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Test
    void deleteStatusCancelOfOrderAbsentReturnsInternalServerError() throws Exception {
        mockMvc.perform(delete("/orders/-100/delete"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @WithMockUser(roles = "USER")
    @Test
    void deleteStatusCancelOfOrderCancelledOrCompleteReturnsMethodNotAllowed() throws Exception {
        mockMvc.perform(delete("/orders/8/delete"))
                .andDo(print());
        mockMvc.perform(delete("/orders/8/delete"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }
}
