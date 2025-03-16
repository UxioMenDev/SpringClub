package com.spring.club.services;

import java.util.List;
import com.spring.club.entities.Player;

public interface PlayerService {
    void create(Player p);
    List<Player> findAll();
    Player findById(int id);
    void delete(Player p);
}