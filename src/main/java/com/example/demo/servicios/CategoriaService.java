package com.example.demo.servicios;

import java.util.List;

import com.example.demo.controllers.entities.Categoria;

public interface CategoriaService {
  public List<Categoria> findAll();

  public Categoria findById(int id);

  public void crear(Categoria c);

}
