package com.spring.club.services;

import java.util.List;
import com.spring.club.entities.Team;

public interface TeamService {
    void create(Team t);
    List<Team> findAll();
    Team findById(int id);
    void delete(Team t);
}