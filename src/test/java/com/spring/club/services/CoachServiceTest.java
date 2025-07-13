package com.spring.club.services;

import com.spring.club.entities.Coach;
import com.spring.club.entities.Country;
import com.spring.club.entities.enums.Gender;
import com.spring.club.repositories.CoachRepository;
import com.spring.club.repositories.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CoachServiceTest {

    @Autowired private CoachRepository coachRepository;
    @Autowired private CountryRepository countryRepository;

    @Test
    void testCreateCoach() {
        Country country = new Country();
        country.setName("España");
        country = countryRepository.save(country);

        Coach coach = new Coach();
        coach.setName("Pedro");

        coach = coachRepository.save(coach);

        assertThat(coach.getId()).isNotNull();
    }
}