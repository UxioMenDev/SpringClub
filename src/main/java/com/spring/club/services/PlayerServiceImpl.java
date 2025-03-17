package com.spring.club.services;

import java.util.List;

import com.spring.club.entities.enums.Category;
import org.springframework.stereotype.Service;
import com.spring.club.entities.Player;
import com.spring.club.repositories.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void create(Player p) {
        playerRepository.save(p);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(int id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Player p) {
        playerRepository.delete(p);
    }

    @Override
    public List<Player> findByCategory(Category category) {
        return playerRepository.findByCategory(category);
    }
}