package com.spring.club.services;

import java.util.List;
import com.spring.club.entities.Player;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;

public interface PlayerService {
    void create(Player p);
    List<Player> findAll();
    Player findById(int id);
    void delete(Player p);
    List<Player> findByCategoryAndSeason(Long season_id, Category category);
    List<Player> findByGenderAndSeason(Long season_id, Gender gender);
    List<Player> findByCategoryAndGenderIntersection(Long season_id, Category category, Gender gender);
    void renovate(List<Long> ids);

}