package com.spring.club.services;

import java.util.List;
import com.spring.club.controllers.entities.Coach;

public interface CoachService {
    void create(Coach c);
    List<Coach> findAll();
    Coach findById(int id);
    void delete(Coach c);
}