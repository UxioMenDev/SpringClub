package com.example.demo.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controllers.entities.Categoria;
import com.example.demo.repositorios.CategoriaRepositorio;

@Service
public class CategoriaServiceImpl implements CategoriaService {

  private CategoriaRepositorio categoriaRepositorio;

  public CategoriaServiceImpl(CategoriaRepositorio categoriaRepositorio) {
    this.categoriaRepositorio = categoriaRepositorio;
  }

  @Override
  public List<Categoria> findAll() {

    List<Categoria> categorias = categoriaRepositorio.findAll();
    return categorias;

  }

  @Override
  public Categoria findById(int id) {
    Categoria c = categoriaRepositorio.findById(id);
    return c;
  }

  @Override
  public void crear(Categoria c) {
    categoriaRepositorio.save(c);
  }
}
