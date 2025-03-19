package com.spring.club.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            teamRepository.save(t);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Ya existe un equipo para esta categoría y género en la temporada actual");
        }
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

    @Override
    public List<Team> findBySeasonId(Long seasonId) {
        return teamRepository.findBySeasonId(seasonId);
    }
}