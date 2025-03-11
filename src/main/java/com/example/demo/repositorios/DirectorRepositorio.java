package com.example.demo.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.controllers.entities.Director;

@Repository
public interface DirectorRepositorio extends JpaRepository<Director, Integer> {

  public List<Director> findAll();

  public Director findById(int id);

}
