package com.spring.club.services;

import com.spring.club.entities.Season;

public interface SeasonService {

    Season getCurrentSeason();
    Season createNewSeason();
    void updateSeason();


}
