package com.spring.club.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.club.entities.PlayerSeason;
import com.spring.club.entities.Season;
import com.spring.club.entities.User;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;
import org.springframework.stereotype.Service;
import com.spring.club.entities.Player;
import com.spring.club.repositories.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private SeasonService seasonService;
    private UserService userService;
    private  PlayerSeasonService playerSeasonService;



    public PlayerServiceImpl(PlayerRepository playerRepository, SeasonService seasonService, UserService userService, PlayerSeasonService playerSeasonService) {
        this.playerRepository = playerRepository;
        this.seasonService = seasonService;
        this.userService = userService;
        this.playerSeasonService = playerSeasonService;
    }

    @Override
    public void create(Player p, String username, boolean paid) {
        User currentUser = userService.findByUsername(username);
        p.setUser(currentUser);
        Player savedPlayer = playerRepository.save(p);
        playerSeasonService.create(savedPlayer, seasonService.getCurrentSeason(), paid);
    }

    @Override
    public void update(Player player) {
        playerRepository.save(player);
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
    public List<Player> findBySeason(Long seasonId) {
        return playerRepository.findByPlayerSeasons_Season_Id(seasonId);
    }
    @Override
    public List<Player> findByCategoryAndSeason(Long seasonId, Category category) {
        return playerRepository.findByPlayerSeasons_Season_IdAndCategory(seasonId, category);
    }

    @Override
    public List<Player> findByGenderAndSeason(Long seasonId, Gender gender) {
        return playerRepository.findByPlayerSeasons_Season_IdAndSex(seasonId, gender);
    }

    @Override
    public List<Player> findByCategoryAndGenderIntersection(Long season_id, Category category, Gender gender) {
        List<Player> categoryPlayers = findByCategoryAndSeason(season_id, category);
        List<Player> genderPlayers = findByGenderAndSeason(season_id, gender);

        return categoryPlayers.stream()
                .filter(genderPlayers::contains)
                .collect(Collectors.toList());
    }

    @Override
    public void renovate(List<Long> ids) {
        Season currentSeason = seasonService.getCurrentSeason();
        for (Long id : ids) {
            Player player = playerRepository.findById(id.intValue())
                    .orElseThrow(() -> new IllegalArgumentException("Player not found with id: " + id));
            playerSeasonService.create(player, currentSeason, false);
        }

    }

    @Override
    public List<Player> findByUser(User user) {
        return playerRepository.findByUser(user);
    }

    @Override
    public List<Player> findByUserAndSeason(User user, Long seasonId) {
        return playerRepository.findByUserAndPlayerSeasons_Season_Id(user, seasonId);
    }


}