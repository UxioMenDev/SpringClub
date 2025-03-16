package com.spring.club.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.spring.club.entities.Coach;
import com.spring.club.repositories.CoachRepository;

@Service
public class CoachServiceImpl implements CoachService {

    private CoachRepository coachRepository;

    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public void create(Coach c) {
        coachRepository.save(c);
    }

    @Override
    public List<Coach> findAll() {
        return coachRepository.findAll();
    }

    @Override
    public Coach findById(int id) {
        return coachRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Coach c) {
        coachRepository.delete(c);
    }
}