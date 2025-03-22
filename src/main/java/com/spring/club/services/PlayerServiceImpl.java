package com.spring.club.services;

import java.util.List;
import java.util.stream.Collectors;

import com.spring.club.entities.Season;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;
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
    public List<Player> findByCategoryAndSeason(Long season_id, Category category) {
        return playerRepository.findBySeasons_IdAndCategory(season_id, category);
    }

    @Override
    public List<Player> findByGenderAndSeason(Long season_id,Gender gender) {
        return playerRepository.findBySeasons_IdAndSex(season_id, gender);
    }

    @Override
    public List<Player> findByCategoryAndGenderIntersection(Long season_id, Category category, Gender gender) {
        List<Player> categoryPlayers = findByCategoryAndSeason(season_id, category);
        List<Player> genderPlayers = findByGenderAndSeason(season_id, gender);

        return categoryPlayers.stream()
                .filter(genderPlayers::contains)
                .collect(Collectors.toList());
    }


}