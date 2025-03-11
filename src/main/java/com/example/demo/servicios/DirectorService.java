package com.example.demo.servicios;

import java.util.List;

import com.example.demo.controllers.entities.Director;

public interface DirectorService {

  public void crear(Director p);

  public List<Director> findAll();

  public Director findById(int id);

  public void borrar(Director p);

}
