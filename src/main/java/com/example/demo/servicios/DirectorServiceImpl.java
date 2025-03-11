package com.example.demo.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controllers.entities.Director;
import com.example.demo.repositorios.DirectorRepositorio;

@Service
public class DirectorServiceImpl implements DirectorService {

  private DirectorRepositorio directorRepositorio;

  public DirectorServiceImpl(DirectorRepositorio directorRepositorio) {
    this.directorRepositorio = directorRepositorio;
  }

  @Override
  public void crear(Director d) {
    directorRepositorio.save(d);
  }

  @Override
  public List<Director> findAll() {
    List<Director> directores = directorRepositorio.findAll();
    return directores;
  }

  @Override
  public Director findById(int id) {
    Director d = directorRepositorio.findById(id);
    return d;
  }

  @Override
  public void borrar(Director p) {
    directorRepositorio.delete(p);
  }

}
