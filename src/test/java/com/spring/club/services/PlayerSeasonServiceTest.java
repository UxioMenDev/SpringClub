package com.spring.club.services;

import com.spring.club.entities.*;
import com.spring.club.entities.enums.Gender;
import com.spring.club.repositories.PlayerRepository;
import com.spring.club.repositories.PlayerSeasonRepository;
import com.spring.club.repositories.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlayerSeasonServiceTest {

    @Autowired private PlayerSeasonRepository playerSeasonRepository;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private SeasonRepository seasonRepository;


    @Test
    @DirtiesContext
    void testCreatePlayerSeason() {
        Player player = new Player();
        player.setName("Juan");
        player.setBirthdate(new Date());
        player.setEmail("juan@email.com");
        player.setIdCard("12345678A");
        player.setPhone(600123456);
        player.setSex(Gender.MASCULINO);
        player.setStreet("Calle Mayor 1");
        player.setZip(28001);
        player.setPlaceOfBirth("Madrid");
        player.setCountry("España");
        player = playerRepository.save(player);

        Season season = new Season();
        season.setSeason("2024/2025");
        season = seasonRepository.save(season);

        PlayerSeason playerSeason = new PlayerSeason();
        playerSeason.setPlayer(player);
        playerSeason.setSeason(season);
        playerSeason.setPaid(true);

        playerSeason = playerSeasonRepository.save(playerSeason);

        assertThat(playerSeason.getId()).isNotNull();
    }
}