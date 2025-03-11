package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.controllers.entities.Usuario;
import com.example.demo.servicios.UsuariosService;

@Controller
public class UsuariosController {

  private UsuariosService usuariosService;

  public UsuariosController(UsuariosService usuariosService) {
    this.usuariosService = usuariosService;
  }

  @GetMapping("/registro")
  public String registrar(@ModelAttribute Usuario u) {
    return "usuarios/registro";
  }

  @PostMapping("/add")
  public String guardar(@ModelAttribute Usuario u) {
    usuariosService.crear(u);
    return "usuarios/login";
  }

  @GetMapping("/login")
  public String mostrarLogin(@ModelAttribute Usuario u) {
    return "usuarios/login";
  }

}
