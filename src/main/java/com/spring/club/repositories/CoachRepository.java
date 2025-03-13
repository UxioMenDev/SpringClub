package com.spring.club.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.club.controllers.entities.Coach;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {
    public List<Coach> findAll();
    Optional<Coach> findById(int id);
}