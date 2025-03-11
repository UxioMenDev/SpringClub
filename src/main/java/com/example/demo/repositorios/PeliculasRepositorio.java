package com.example.demo.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.controllers.entities.Categoria;
import com.example.demo.controllers.entities.Pelicula;

@Repository
public interface PeliculasRepositorio extends JpaRepository<Pelicula, Integer> {
  public List<Pelicula> findAll();

  public Pelicula findById(int id);

  public List<Pelicula> findByCategorias(Categoria c);

}
