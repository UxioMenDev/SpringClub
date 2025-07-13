
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

    @Autowired
    private CountryRepository countryRepository;

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

        com.spring.club.entities.Country country = new com.spring.club.entities.Country();
        country.setName("España");
        country = countryRepository.save(country);

        player.setCountry(country);

        player = playerRepository.save(player);

        assertThat(playerRepository.findById(player.getId())).isPresent();
    }
}