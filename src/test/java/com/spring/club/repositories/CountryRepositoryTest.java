package com.spring.club.repositories;

import com.spring.club.entities.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void testExistsByName() {
        Country country = new Country();
        country.setName("España");
        countryRepository.save(country);

        assertThat(countryRepository.existsByName("España")).isTrue();
    }
}