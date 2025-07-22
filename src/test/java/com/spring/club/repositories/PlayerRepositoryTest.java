package com.spring.club.repositories;

import com.spring.club.entities.Player;
import com.spring.club.entities.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;


    @Test
    void testFindById() {
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
        player.setCountry("España");

        player = playerRepository.save(player);

        assertThat(playerRepository.findById(player.getId())).isPresent();
    }
}