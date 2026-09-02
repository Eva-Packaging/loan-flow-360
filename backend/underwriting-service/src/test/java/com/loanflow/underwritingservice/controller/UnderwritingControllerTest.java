package com.loanflow.underwritingservice.controller;

import com.loanflow.underwritingservice.service.UnderwritingDecisionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UnderwritingController.class)
class UnderwritingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UnderwritingDecisionService underwritingDecisionService;

    @Test
    void testGetUnderwritingDecision() throws Exception {
        when(underwritingDecisionService.getDecision(1L))
                .thenReturn(com.loanflow.underwritingservice.dto.UnderwritingDecisionResponse.builder()
                        .applicationId(1L)
                        .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/underwriting/{applicationId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.applicationId").value(1L));
    }
}
