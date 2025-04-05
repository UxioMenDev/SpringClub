package com.spring.club.services;

import com.spring.club.entities.Player;
import com.spring.club.entities.PlayerSeason;
import com.spring.club.entities.Season;

import java.util.List;

public interface PlayerSeasonService {
    void create(Player player, Season season, boolean paid);
    boolean isPlayerPaidForSeason(Player player, Season season);
    List<PlayerSeason> findByPlayer(Player player);
    List<PlayerSeason> findBySeason(Season season);
    void updatePaymentStatus(Player player, Season season, boolean paid);
}