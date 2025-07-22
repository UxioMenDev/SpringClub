package com.spring.club.controllers;

import com.spring.club.entities.Coach;
import com.spring.club.services.CoachService;
import com.spring.club.services.SeasonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class CoachControllerTest {

    @Mock
    private CoachService coachService;
    @Mock
    private SeasonService seasonService;

    private MockMvc mockMvc;

    void setupMockMvc() {
        CoachController controller = new CoachController(coachService, seasonService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testShowForm() throws Exception {
        setupMockMvc();
        var result = mockMvc.perform(get("/coach/form"))
                .andReturn();
        assertThat(result.getResponse().getForwardedUrl()).isEqualTo("coaches/formCoach");
    }

    @Test
    void testShowCoaches() throws Exception {
        setupMockMvc();
        Mockito.when(coachService.findAll()).thenReturn(Collections.emptyList());
        var result = mockMvc.perform(get("/coach/list"))
                .andReturn();
        assertThat(result.getResponse().getForwardedUrl()).isEqualTo("coaches/showCoaches");
    }

    @Test
    void testEditCoach() throws Exception {
        setupMockMvc();
        Coach coach = new Coach();
        Mockito.when(coachService.findById(1)).thenReturn(coach);
        var result = mockMvc.perform(get("/coach/edit").param("id", "1"))
                .andReturn();
        assertThat(result.getResponse().getForwardedUrl()).isEqualTo("coaches/formCoach");
    }
}