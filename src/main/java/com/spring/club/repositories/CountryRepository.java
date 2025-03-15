package com.spring.club.repositories;

import com.spring.club.controllers.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByName(String name);
}