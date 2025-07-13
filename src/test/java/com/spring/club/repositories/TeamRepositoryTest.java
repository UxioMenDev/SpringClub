package com.spring.club.repositories;

import com.spring.club.entities.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void testFindById() {
        Team team = new Team();
        team = teamRepository.save(team);

        assertThat(teamRepository.findById(team.getId())).isPresent();
    }
}