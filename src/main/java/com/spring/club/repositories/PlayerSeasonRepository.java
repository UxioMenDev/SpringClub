package com.spring.club.repositories;

import com.spring.club.entities.Player;
import com.spring.club.entities.PlayerSeason;
import com.spring.club.entities.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerSeasonRepository extends JpaRepository<PlayerSeason, Long> {
    Optional<PlayerSeason> findByPlayerAndSeason(Player player, Season season);
    List<PlayerSeason> findByPlayer(Player player);
    List<PlayerSeason> findBySeason(Season season);
}