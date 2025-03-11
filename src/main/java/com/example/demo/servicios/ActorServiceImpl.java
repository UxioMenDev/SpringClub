package com.example.demo.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controllers.entities.Actor;
import com.example.demo.repositorios.ActorRepositorio;

@Service
public class ActorServiceImpl implements ActorService {

  private ActorRepositorio actorRepositorio;

  public ActorServiceImpl(ActorRepositorio actorRepositorio) {
    this.actorRepositorio = actorRepositorio;
  }

  @Override
  public void crear(Actor a) {
    actorRepositorio.save(a);
  }

  @Override
  public List<Actor> findAll() {
    List<Actor> actores = actorRepositorio.findAll();
    return actores;
  }

  @Override
  public Actor findById(int id) {
    Actor actor = actorRepositorio.findById(id);
    return actor;
  }

  @Override
  public void borrar(Actor a) {
    actorRepositorio.delete(a);
  }

}
