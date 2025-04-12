package com.spring.club.controllers;

import com.spring.club.dto.PaymentRequest;
import com.spring.club.entities.Player;
import com.spring.club.entities.PlayerSeason;
import com.spring.club.repositories.PlayerSeasonRepository;
import com.spring.club.services.PlayerSeasonService;
import com.spring.club.services.PlayerService;
import com.spring.club.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PlayerSeasonService playerSeasonService;
    private final SeasonService seasonService;
    private final PlayerService playerService;

    public PaymentController(PlayerSeasonService playerSeasonService,
                             SeasonService seasonService,
                             PlayerService playerService) {
        this.playerSeasonService = playerSeasonService;
        this.seasonService = seasonService;
        this.playerService = playerService;
    }

    @PostMapping("/payment")
    public ResponseEntity<?> handlePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            Player player = playerService.findById(paymentRequest.getPlayerId());
            PlayerSeason playerSeason = (PlayerSeason) playerSeasonService.findByPlayerAndSeason(
                    player,
                    seasonService.getCurrentSeason()
            ).orElse(null);

            if (playerSeason != null) {
                playerSeasonService.updatePayment(playerSeason);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
