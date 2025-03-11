package com.example.demo.servicios;

import java.util.List;

import com.example.demo.controllers.entities.Actor;

public interface ActorService {

  public void crear(Actor a);

  public List<Actor> findAll();

  public Actor findById(int id);

  public void borrar(Actor a);

}
