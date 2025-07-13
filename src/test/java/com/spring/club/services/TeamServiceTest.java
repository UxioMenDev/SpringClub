package com.spring.club.services;

import com.spring.club.entities.*;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;
import com.spring.club.repositories.CountryRepository;
import com.spring.club.repositories.SeasonRepository;
import com.spring.club.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TeamServiceTest {

    @Autowired private TeamRepository teamRepository;
    @Autowired private SeasonRepository seasonRepository;


    @Test
    @DirtiesContext
    void testCreateTeam() {
        Season season = new Season();
        season.setSeason("2024/2025");
        season = seasonRepository.save(season);

        Team team = new Team();
        team.setSeason(season);
        team.setCategory(Category.ABSOLUTO);
        team.setGender(Gender.FEMENINO);
        team.setPlayers(new HashSet<>());

        team = teamRepository.save(team);

        assertThat(team.getId()).isNotNull();
    }
}