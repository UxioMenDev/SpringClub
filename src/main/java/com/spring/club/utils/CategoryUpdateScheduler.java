package com.spring.club.utils;

import com.spring.club.entities.Player;
import com.spring.club.services.PlayerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class CategoryUpdateScheduler {

    private final PlayerService playerService;

    public CategoryUpdateScheduler(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Scheduled(cron = "0 0 0 30 9 *")
    public void updateCategories() {
        List<Player> players = playerService.findAll();
        for (Player player : players) {
            player.calculateCategory();
            playerService.update(player);
        }
    }}