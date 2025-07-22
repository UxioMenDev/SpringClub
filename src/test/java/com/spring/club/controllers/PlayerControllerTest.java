package com.spring.club.controllers;

import com.spring.club.entities.Player;
import com.spring.club.entities.Season;
import com.spring.club.entities.User;
import com.spring.club.entities.enums.Role;
import com.spring.club.services.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @MockBean private PlayerService playerService;
    @MockBean private CountryApiService countryApiService;
    @MockBean private SeasonService seasonService;
    @MockBean private TeamService teamService;
    @MockBean private UserService userService;
    @MockBean private StorageService storageService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testShowForm() throws Exception {
        User user = new User();
        user.setRoles(Set.of(Role.ROLE_ADMIN));

        List<String> countries = Arrays.asList("España", "Francia", "Italia");
        when(countryApiService.fetchCountriesFromApi()).thenReturn(countries);

        when(userService.findByUsername("admin")).thenReturn(user);
        when(seasonService.getCurrentSeason()).thenReturn(new Season());
        when(userService.findAll()).thenReturn(Collections.emptyList());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User("admin", "password", Collections.emptyList());

        var result = mockMvc.perform(get("/player/form").with(user(userDetails)))
                .andReturn();
        assertThat(result.getModelAndView().getViewName()).isEqualTo("players/formPlayer");
    }

    @Test
    void testShowPlayers() throws Exception {
        User user = new User();
        user.setRoles(Set.of(Role.ROLE_ADMIN));
        when(userService.findByUsername("admin")).thenReturn(user);
        when(playerService.findAll()).thenReturn(Collections.emptyList());
        when(seasonService.findAll()).thenReturn(Collections.emptyList());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User("admin", "password", Collections.emptyList());

        var result = mockMvc.perform(get("/player/list").with(user(userDetails)))
                .andReturn();

        Exception ex = result.getResolvedException();
        if (ex != null) {
            ex.printStackTrace();
            throw ex;
        }

        assertThat(result.getModelAndView().getViewName()).isEqualTo("players/showPlayers");
    }

    @Test
    void testEditPlayer() throws Exception {
        Player player = new Player();
        when(playerService.findById(1)).thenReturn(player);
        when(countryApiService.fetchCountriesFromApi()).thenReturn(Collections.emptyList());
        when(userService.findAll()).thenReturn(Collections.emptyList());
        when(seasonService.getCurrentSeason()).thenReturn(new Season());
        User user = new User();
        user.setRoles(Set.of(Role.ROLE_ADMIN));
        when(userService.findByUsername("admin")).thenReturn(user);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User("admin", "password", Collections.emptyList());

        var result = mockMvc.perform(get("/player/edit").param("id", "1").with(user(userDetails)))
                .andReturn();
        assertThat(result.getModelAndView().getViewName()).isEqualTo("players/formPlayer");
    }
}