package com.spring.club.repositories;

import com.spring.club.entities.Season;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SeasonRepositoryTest {

    @Autowired
    private SeasonRepository seasonRepository;

    @Test
    void testFindBySeason() {
        Season season = new Season();
        season.setSeason("2024");
        seasonRepository.save(season);

        assertThat(seasonRepository.findBySeason("2024")).isPresent();
    }
}