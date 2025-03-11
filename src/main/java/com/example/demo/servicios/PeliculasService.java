package com.example.demo.servicios;

import java.util.List;

import com.example.demo.controllers.entities.Categoria;
import com.example.demo.controllers.entities.Pelicula;

public interface PeliculasService {

  public void crear(Pelicula p);

  public List<Pelicula> findAll();

  public Pelicula findById(int id);

  public void borrar(Pelicula p);

  public List<Pelicula> findByCategoria(Categoria c);

}
