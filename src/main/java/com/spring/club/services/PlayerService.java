package com.spring.club.services;

import java.util.List;
import com.spring.club.entities.Player;
import com.spring.club.entities.User;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;

public interface PlayerService {
    void create(Player p, String username, boolean paid);
    void update(Player player);
    List<Player> findAll();
    Player findById(int id);
    void delete(Player p);
    List<Player> findBySeason(Long season_id);
    List<Player> findByCategoryAndSeason(Long season_id, Category category);
    List<Player> findByGenderAndSeason(Long season_id, Gender gender);
    List<Player> findByCategoryAndGenderIntersection(Long season_id, Category category, Gender gender);
    void renovate(List<Long> ids);
    List<Player> findByUser(User user);
    List<Player> findByUserAndSeason(User user, Long season_id);

}