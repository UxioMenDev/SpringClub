package com.spring.club.services;

import com.spring.club.entities.Season;
import com.spring.club.repositories.SeasonRepository;
import com.spring.club.utils.SeasonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

    public Season getCurrentSeason() {
        return seasonRepository.findBySeason(SeasonUtil.getCurrentSeason())
                .orElseGet(() -> createNewSeason());
    }

    public Season createNewSeason() {
        String newSeason = SeasonUtil.getCurrentSeason();
        Season season = new Season();
        season.setSeason(newSeason);
        return seasonRepository.save(season);
    }

    public void updateSeason() {
        Season current = getCurrentSeason();
        int first = Integer.parseInt(current.getSeason().split("/")[0]);
        int nextFirst = first + 1;
        int nextSecond = nextFirst + 1;
        String nextSeason = String.format("%d/%d", nextFirst, nextSecond);

        Season newSeason = new Season();
        newSeason.setSeason(nextSeason);
        seasonRepository.save(newSeason);
    }

    @Override
    public List<Season> findAll() {
        return seasonRepository.findAll();
    }
}
