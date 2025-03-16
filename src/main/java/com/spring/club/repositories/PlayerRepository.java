package com.spring.club.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.club.entities.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    public List<Player> findAll();
    Optional<Player> findById(int id);
}