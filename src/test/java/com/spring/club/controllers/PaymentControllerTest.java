package com.spring.club.controllers;

import com.spring.club.dto.PaymentRequest;
import com.spring.club.entities.Player;
import com.spring.club.entities.PlayerSeason;
import com.spring.club.entities.Season;
import com.spring.club.services.PlayerSeasonService;
import com.spring.club.services.PlayerService;
import com.spring.club.services.SeasonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock private PlayerSeasonService playerSeasonService;
    @Mock private SeasonService seasonService;
    @Mock private PlayerService playerService;

    private MockMvc mockMvc;

    void setupMockMvc() {
        PaymentController controller = new PaymentController(playerSeasonService, seasonService, playerService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void testHandlePaymentSuccess() throws Exception {
        setupMockMvc();
        Player player = new Player();
        PlayerSeason playerSeason = new PlayerSeason();
        Mockito.when(playerService.findById(1)).thenReturn(player); // <-- corregido
        Mockito.when(seasonService.getCurrentSeason()).thenReturn(new Season());
        Mockito.when(playerSeasonService.findByPlayerAndSeason(Mockito.eq(player), Mockito.any())).thenReturn(Optional.of(playerSeason));

        Mockito.doNothing().when(playerSeasonService).updatePayment(playerSeason);

        String json = "{\"playerId\":1}";
        mockMvc.perform(post("/api/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testHandlePaymentNotFound() throws Exception {
        setupMockMvc();
        Player player = new Player();
        Mockito.when(playerService.findById(1)).thenReturn(player); // <-- corregido
        Mockito.when(seasonService.getCurrentSeason()).thenReturn(new Season());
        Mockito.when(playerSeasonService.findByPlayerAndSeason(Mockito.eq(player), Mockito.any())).thenReturn(Optional.empty());

        String json = "{\"playerId\":1}";
        mockMvc.perform(post("/api/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }
}