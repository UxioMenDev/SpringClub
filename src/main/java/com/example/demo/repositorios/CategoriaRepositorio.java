package com.example.demo.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.controllers.entities.Categoria;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {
  public List<Categoria> findAll();
  public Categoria  findById(int id);

}
