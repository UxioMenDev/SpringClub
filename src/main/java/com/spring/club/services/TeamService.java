package com.spring.club.services;

import java.util.List;

import com.spring.club.entities.Season;
import com.spring.club.entities.Team;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;

public interface TeamService {
    void create(Team t);
    List<Team> findAll();
    Team findById(int id);
    void delete(Team t);
    List<Team> findBySeasonId(Long SeasonId);
    List<Team> findBySeasonAndCategoryAndGender(Season season, Category category, Gender gender);
}