package com.spring.club.services;

import com.spring.club.entities.Player;
import com.spring.club.entities.PlayerSeason;
import com.spring.club.entities.Season;

import java.util.List;
import java.util.Optional;

public interface PlayerSeasonService {
    void create(Player player, Season season, boolean paid);
    Optional findByPlayerAndSeason(Player player, Season season);
    List<PlayerSeason> findByPlayer(Player player);
    List<PlayerSeason> findBySeason(Season season);
    void updatePayment(PlayerSeason playerSeasonseason);
}