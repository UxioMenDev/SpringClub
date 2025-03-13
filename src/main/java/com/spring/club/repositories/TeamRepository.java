package com.spring.club.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.club.controllers.entities.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    public List<Team> findAll();
    Optional<Team> findById(int id);
}