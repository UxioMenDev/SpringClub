package com.spring.club.repositories;

import java.util.List;
import java.util.Optional;

import com.spring.club.entities.User;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.club.entities.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    public List<Player> findAll();
    Optional<Player> findById(int id);
    List<Player> findBySeasons_Id(Long seasonId);
    List<Player> findBySeasons_IdAndCategory(Long seasonId, Category category);
    List<Player> findBySeasons_IdAndSex(Long seasonId, Gender gender);
    List<Player> findByUser(User user);
    List<Player> findByUserAndSeasons_Id(User user, Long seasonId);

}