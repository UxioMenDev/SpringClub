package com.spring.club.controllers;

import com.spring.club.entities.Team;
import com.spring.club.entities.Season;
import com.spring.club.services.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock private TeamService teamService;
    @Mock private CoachService coachService;
    @Mock private PlayerService playerService;
    @Mock private SeasonService seasonService;

    private MockMvc mockMvc;

    void setupMockMvc() {
        TeamController controller = new TeamController(teamService, coachService, playerService, seasonService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testShowForm() throws Exception {
        setupMockMvc();
        Mockito.when(seasonService.getCurrentSeason()).thenReturn(new Season());
        Mockito.when(coachService.findAll()).thenReturn(Collections.emptyList());
        var result = mockMvc.perform(get("/teams/form"))
                .andReturn();
        assertThat(result.getResponse().getForwardedUrl()).isEqualTo("teams/formTeam");
    }

    @Test
    void testShowTeams() throws Exception {
        setupMockMvc();
        Mockito.when(teamService.findAll()).thenReturn(Collections.emptyList());
        var result = mockMvc.perform(get("/teams/list"))
                .andReturn();
        assertThat(result.getResponse().getForwardedUrl()).isEqualTo("teams/showTeams");
    }

    @Test
    void testEditTeam() throws Exception {
        setupMockMvc();
        Team team = new Team();
        Mockito.when(teamService.findById(1)).thenReturn(team);
        Mockito.when(seasonService.getCurrentSeason()).thenReturn(new Season());
        Mockito.when(coachService.findAll()).thenReturn(Collections.emptyList());
        Mockito.when(playerService.findByCategoryAndGenderIntersection(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Collections.emptyList());

        var result = mockMvc.perform(get("/teams/edit").param("id", "1"))
                .andReturn();
        assertThat(result.getResponse().getForwardedUrl()).isEqualTo("teams/formTeam");
    }
}