package com.example.demo.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controllers.entities.Categoria;
import com.example.demo.controllers.entities.Pelicula;
import com.example.demo.repositorios.PeliculasRepositorio;

@Service
public class PeliculasServiceImpl implements PeliculasService {

  private PeliculasRepositorio peliculasRepositorio;

  public PeliculasServiceImpl(PeliculasRepositorio peliculasRepositorio) {
    this.peliculasRepositorio = peliculasRepositorio;
  }

  @Override
  public void crear(Pelicula p) {
    peliculasRepositorio.save(p);
  }

  @Override
  public List<Pelicula> findAll() {
    List<Pelicula> peliculas = peliculasRepositorio.findAll();
    return peliculas;
  }

  @Override
  public Pelicula findById(int id) {
    Pelicula p = peliculasRepositorio.findById(id);
    return p;
  }

  @Override
  public void borrar(Pelicula p) {
    peliculasRepositorio.delete(p);
  }

  @Override
  public List<Pelicula> findByCategoria(Categoria c) {
    List<Pelicula> peliculas = peliculasRepositorio.findByCategorias(c);
    return peliculas;
  }

}
