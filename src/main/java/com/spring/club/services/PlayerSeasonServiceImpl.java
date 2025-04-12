package com.spring.club.services;

import com.spring.club.entities.Player;
import com.spring.club.entities.PlayerSeason;
import com.spring.club.entities.Season;
import com.spring.club.repositories.PlayerSeasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerSeasonServiceImpl implements PlayerSeasonService {

    private final PlayerSeasonRepository playerSeasonRepository;

    public PlayerSeasonServiceImpl(PlayerSeasonRepository playerSeasonRepository) {
        this.playerSeasonRepository = playerSeasonRepository;
    }

    @Override
    public void create(Player player, Season season, boolean paid) {
        PlayerSeason playerSeason = new PlayerSeason();
        playerSeason.setPlayer(player);
        playerSeason.setSeason(season);
        playerSeason.setPaid(paid);
        playerSeasonRepository.save(playerSeason);
    }

    @Override
    public Optional findByPlayerAndSeason(Player player, Season season) {
        return playerSeasonRepository.findByPlayerAndSeason(player, season);
    }

    @Override
    public List<PlayerSeason> findByPlayer(Player player) {
        return playerSeasonRepository.findByPlayer(player);
    }

    @Override
    public List<PlayerSeason> findBySeason(Season season) {
        return playerSeasonRepository.findBySeason(season);
    }

    @Override
    public void updatePayment(PlayerSeason playerSeason) {
        playerSeason.setPaid(true);
        playerSeasonRepository.save(playerSeason);
    }
}