package com.spring.club.services;

import com.spring.club.entities.Season;

import java.util.List;

public interface SeasonService {

    Season getCurrentSeason();
    Season createNewSeason();
    void updateSeason();
    List<Season> findAll();


}
