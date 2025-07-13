package com.spring.club.services;

import com.spring.club.entities.Season;
import com.spring.club.repositories.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SeasonServiceTest {

    @Autowired private SeasonRepository seasonRepository;

    @Test
    @DirtiesContext
    void testCreateSeason() {
        Season season = new Season();
        season.setSeason("2024/2025");
        season = seasonRepository.save(season);

        assertThat(season.getId()).isNotNull();
    }
}