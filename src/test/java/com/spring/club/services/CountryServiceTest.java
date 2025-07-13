package com.spring.club.services;

import com.spring.club.entities.Country;
import com.spring.club.repositories.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CountryServiceTest {

    @Autowired private CountryRepository countryRepository;

    @Test
    void testCreateCountry() {
        Country country = new Country();
        country.setName("Francia");
        country = countryRepository.save(country);

        assertThat(country.getId()).isNotNull();
        assertThat(countryRepository.existsByName("Francia")).isTrue();
    }
}