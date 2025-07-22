package com.spring.club.services;

import com.spring.club.entities.Coach;
import com.spring.club.repositories.CoachRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CoachServiceTest {

    @Autowired private CoachRepository coachRepository;

    @Test
    void testCreateCoach() {

        Coach coach = new Coach();
        coach.setName("Pedro");

        coach = coachRepository.save(coach);

        assertThat(coach.getId()).isNotNull();
    }
}