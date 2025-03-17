package com.spring.club.services;

import java.util.List;
import com.spring.club.entities.Player;
import com.spring.club.entities.enums.Category;

public interface PlayerService {
    void create(Player p);
    List<Player> findAll();
    Player findById(int id);
    void delete(Player p);
    List<Player> findByCategory(Category category);
}