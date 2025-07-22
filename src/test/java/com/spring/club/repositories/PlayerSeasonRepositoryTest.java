package com.spring.club.repositories;

import com.spring.club.entities.Player;
import com.spring.club.entities.PlayerSeason;
import com.spring.club.entities.Season;
import com.spring.club.entities.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlayerSeasonRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private PlayerSeasonRepository playerSeasonRepository;

    @Test
    void testFindByPlayerAndSeason() {
        Player player = new Player();
        player.setName("Juan");
        player.setBirthdate(new java.util.Date());
        player.setEmail("juan@email.com");
        player.setIdCard("12345678A");
        player.setPhone(600123456);
        player.setSex(Gender.MASCULINO);
        player.setStreet("Calle Mayor 1");
        player.setZip(28001);
        player.setPlaceOfBirth("Madrid");
        player.setCountry("España"); // Ahora es String
        player = playerRepository.save(player);

        Season season = new Season();
        season.setSeason("2024/2025");
        season = seasonRepository.save(season);

        PlayerSeason playerSeason = new PlayerSeason();
        playerSeason.setPlayer(player);
        playerSeason.setSeason(season);
        playerSeason.setPaid(true);
        playerSeason = playerSeasonRepository.save(playerSeason);

        Optional<PlayerSeason> found = playerSeasonRepository.findByPlayerAndSeason(player, season);
        assertThat(found).isPresent();
    }
}