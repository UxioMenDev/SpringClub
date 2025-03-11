package com.example.demo.servicios;

import com.example.demo.controllers.entities.Usuario;

public interface UsuariosService {

  public void crear(Usuario u);

  public Usuario buscarPorEmail(String email);

}
