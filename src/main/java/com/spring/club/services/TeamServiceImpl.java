package com.spring.club.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.spring.club.entities.Team;
import com.spring.club.repositories.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void create(Team t) {
        teamRepository.save(t);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team findById(int id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Team t) {
        teamRepository.delete(t);
    }
}