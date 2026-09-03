package com.loanflow.underwritingservice.controller;

import com.loanflow.underwritingservice.service.UnderwritingDecisionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UnderwritingController.class)
class UnderwritingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UnderwritingDecisionService underwritingDecisionService;

    @Test
    void testGetUnderwritingDecisionReturnsMatchingApplicationId() throws Exception {
        Random random = new Random();
        long applicationId = random.nextLong();
        when(underwritingDecisionService.getDecision(applicationId))
                .thenReturn(com.loanflow.underwritingservice.dto.UnderwritingDecisionResponse.builder()
                        .applicationId(applicationId)
                        .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/underwriting/{applicationId}", applicationId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.applicationId").value(applicationId));
    }
}
