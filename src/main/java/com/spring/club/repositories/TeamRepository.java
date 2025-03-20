package com.spring.club.repositories;

import java.util.List;
import java.util.Optional;

import com.spring.club.entities.Season;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.club.entities.Team;


@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    public List<Team> findAll();
    Optional<Team> findById(int id);
    List<Team> findBySeasonId(Long seasonId);
    List<Team> findBySeasonAndCategoryAndGender(Season season, Category category, Gender gender);
}