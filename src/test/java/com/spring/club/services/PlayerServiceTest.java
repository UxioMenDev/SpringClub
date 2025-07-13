// src/test/java/com/spring/club/services/PlayerServiceTest.java
package com.spring.club.services;

import com.spring.club.entities.*;
import com.spring.club.entities.enums.Gender;
import com.spring.club.entities.enums.Role;
import com.spring.club.repositories.CountryRepository;
import com.spring.club.repositories.PlayerRepository;
import com.spring.club.repositories.SeasonRepository;
import com.spring.club.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlayerServiceTest {

    @Autowired private PlayerRepository playerRepository;
    @Autowired private CountryRepository countryRepository;
    @Autowired private UserRepository userRepository;


    @Test
    @DirtiesContext
    void testCreatePlayer() {
        Country country = new Country();
        country.setName("Suiza");
        country = countryRepository.save(country);

        User user = new User();
        user.setUsername("usuario");
        user.setRoles(Set.of(Role.ROLE_USER));
        user = userRepository.save(user);

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
        player.setCountry(country);
        player.setUser(user);

        player = playerRepository.save(player);

        assertThat(player.getId()).isNotNull();
    }
}