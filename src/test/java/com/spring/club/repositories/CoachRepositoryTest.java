package com.spring.club.repositories;

import com.spring.club.entities.Coach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CoachRepositoryTest {

    @Autowired
    private CoachRepository coachRepository;

    @Test
    void testFindById() {
        Coach coach = new Coach();
        coach.setName("Pepe");
        coach = coachRepository.save(coach);

        assertThat(coachRepository.findById(coach.getId())).isPresent();
    }
}