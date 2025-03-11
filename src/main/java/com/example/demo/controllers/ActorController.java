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

import com.example.demo.controllers.entities.Actor;
import com.example.demo.servicios.ActorService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/actor")
public class ActorController {

  private ActorService actorService;

  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping("formulario")
  public String mostrarFormulario(@ModelAttribute Actor a, Model model) {
    return "actores/formActor";
  }

  @PostMapping("crear")
  public String crear(@ModelAttribute Actor a) throws IOException {
    MultipartFile imagen = a.getImagen();
    if (!imagen.isEmpty()) {
      String fileName = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename();
      Path path = Paths.get("src/main/resources/static/images/actor/" + fileName);
      Files.copy(imagen.getInputStream(), path);
      a.setRutaImagen("/images/actor/" + fileName);
      System.out.println(a.getRutaImagen());
    }
    actorService.crear(a);
    return "redirect:/actor/lista";
  }

  @GetMapping("lista")
  public String mostrarDirectores(HttpServletRequest request, Model model) {
    model.addAttribute("currentURI", request.getRequestURI());
    List<Actor> actores = actorService.findAll();
    model.addAttribute("actores", actores);
    return "actores/mostrarActores";
  }

  @GetMapping("borrar")
  public String borrar(@RequestParam("id") int id) {
    Actor a = actorService.findById(id);
    try {
      actorService.borrar(a);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/actor/lista";
  }

  @GetMapping("editar")
  public String editar(@RequestParam("id") int id, Model model) throws IOException {
    Actor a = actorService.findById(id);
    MultipartFile imagen = a.getImagen();
    if (imagen != null && !imagen.isEmpty()) {
      String fileName = imagen.getOriginalFilename();
      Path path = Paths.get("src/main/resources/static/images/director/" + fileName);
      Files.copy(imagen.getInputStream(), path);
      a.setRutaImagen("/images/director/" + fileName);
    }
    model.addAttribute("actor", a);
    return "actores/formActor";
  }

}
