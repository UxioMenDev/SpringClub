package com.example.demo.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.controllers.entities.Actor;

@Repository
public interface ActorRepositorio extends JpaRepository<Actor, Integer> {

  public List<Actor> findAll();

  public Actor findById(int id);
}
