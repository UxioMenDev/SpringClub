package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controllers.entities.Director;
import com.example.demo.servicios.DirectorService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/directores")
public class DirectorController {

  private DirectorService directorService;

  public DirectorController(DirectorService directorService) {
    this.directorService = directorService;
  }

  @GetMapping("lista")
  public String mostrarDirectores(HttpServletRequest request, Model model) {
    model.addAttribute("currentURI", request.getRequestURI());
    List<Director> directores = directorService.findAll();
    model.addAttribute("directores", directores);
    return "directores/mostrarDirectores";
  }

  @GetMapping("formulario")
  public String mostrarFormulario(@ModelAttribute Director d, Model model) {
    return "directores/formDirector";
  }

  @PostMapping("crear")
  public String crear(@ModelAttribute Director d) throws IOException {
    MultipartFile imagen = d.getImagen();
    if (!imagen.isEmpty()) {
      String fileName = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename();
      Path path = Paths.get("src/main/resources/static/images/director/" + fileName);
      Files.copy(imagen.getInputStream(), path);
      d.setRutaImagen("/images/director/" + fileName);
    }
    directorService.crear(d);
    return "redirect:/directores/lista";
  }

  @GetMapping("borrar")
  public String borrar(@RequestParam("id") int id) {
    Director d = directorService.findById(id);
    try {
      directorService.borrar(d);
    } catch (Exception e) {
      
      e.printStackTrace();
    }

    return "redirect:/directores/lista";
  }

  @GetMapping("editar")
  public String editar(@RequestParam("id") int id, Model model) throws IOException {
    Director d = directorService.findById(id);
    MultipartFile imagen = d.getImagen();
    if (imagen != null && !imagen.isEmpty()) {
      String fileName = imagen.getOriginalFilename();
      Path path = Paths.get("src/main/resources/static/images/director/" + fileName);
      Files.copy(imagen.getInputStream(), path);
      d.setRutaImagen("/images/director/" + fileName);
    }
    model.addAttribute("director", d);
    return "directores/formDirector";
  }

}
