package com.loanflow.loanservice;

import com.loanflow.loanservice.controller.LoanApplicationController;
import com.loanflow.loanservice.services.LoanApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.loanflow.loanservice.entity.LoanApplicationStatus;
import com.loanflow.loanservice.exception.IllegalStatusTransitionException;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@WebMvcTest(LoanApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)

class LoanApplicationControllerTest {

    @Test
    void shouldReturn409WhenTransitionIsIllegal() throws Exception {

        doThrow(new IllegalStatusTransitionException(
                LoanApplicationStatus.REJECTED,
                LoanApplicationStatus.SUBMITTED))
                .when(loanApplicationService)
                .updateApplicationStatus(
                        org.mockito.ArgumentMatchers.anyLong(),
                        org.mockito.ArgumentMatchers.any()
                );

        mockMvc.perform(
                        patch("/api/v1/applications/1/status")
                                .contentType("application/json")
                                .content("""
                                    {
                                      "status":"SUBMITTED",
                                      "reason":"retry"
                                    }
                                    """)
                )
                .andExpect(status().isConflict())

                .andExpect(
                        jsonPath("$.title")
                                .value("Conflict"))
                .andExpect(jsonPath("$.status")
                        .value(409));
    }

    @Test
    void shouldReturn200ForValidTransition() throws Exception {

        mockMvc.perform(
                        patch("/api/v1/applications/1/status")
                                .contentType("application/json")
                                .content("""
                                {
                                "status" : "APPROVED",
                                "reason" : "approved"
                                }
                                """)
                )
                .andExpect((status() .isOk()));
    }




    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanApplicationService loanApplicationService;


    @Test
    void shouldLoadController() {

    }



}
