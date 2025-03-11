package com.example.demo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.controllers.entities.Actor;
import com.example.demo.controllers.entities.Categoria;
import com.example.demo.controllers.entities.Director;
import com.example.demo.controllers.entities.Pelicula;
import com.example.demo.servicios.ActorService;
import com.example.demo.servicios.CategoriaService;
import com.example.demo.servicios.DirectorService;
import com.example.demo.servicios.PeliculasService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/peliculas")
public class PeliculasController {

  private PeliculasService peliculasService;
  private CategoriaService categoriaService;
  private DirectorService directorService;
  private ActorService actorService;

  public PeliculasController(PeliculasService peliculasService, CategoriaService categoriaService,
      DirectorService directorService, ActorService actorService) {
    this.peliculasService = peliculasService;
    this.categoriaService = categoriaService;
    this.directorService = directorService;
    this.actorService = actorService;
  }

  @GetMapping("formulario")
  public String mostrarFormulario(@ModelAttribute Pelicula p, Model model) {
    List<Categoria> categorias = categoriaService.findAll();
    model.addAttribute("categorias", categorias);
    List<Director> directores = directorService.findAll();
    model.addAttribute("directores", directores);
    List<Actor> actores = actorService.findAll();
    model.addAttribute("actores", actores);
    return "peliculas/formPelicula";
  }

  @PostMapping("crear")
  public String crear(@ModelAttribute Pelicula p) {
    peliculasService.crear(p);
    return "redirect:/peliculas/lista";
  }

  @GetMapping("lista")
  public String mostrarPeliculas(HttpServletRequest request, Model model) {
    model.addAttribute("currentURI", request.getRequestURI());
    List<Pelicula> peliculas = peliculasService.findAll();
    List<Categoria> categorias = categoriaService.findAll();
    model.addAttribute("peliculas", peliculas);
    model.addAttribute("categorias", categorias);
    return "peliculas/mostrarPeliculas";
  }

  @GetMapping("borrar")
  public String borrar(@RequestParam("id") int id) {
    Pelicula p = peliculasService.findById(id);
    peliculasService.borrar(p);
    return "redirect:/peliculas/lista";
  }

  @GetMapping("editar")
  public String editar(@RequestParam("id") int id, Model model) {
    Pelicula p = peliculasService.findById(id);
    model.addAttribute("pelicula", p);
    List<Categoria> categorias = categoriaService.findAll();
    model.addAttribute("categorias", categorias);
    List<Director> directores = directorService.findAll();
    model.addAttribute("directores", directores);
    List<Actor> actores = actorService.findAll();
    model.addAttribute("actores", actores);
    return "peliculas/formPelicula";
  }

  @GetMapping("/filtrar")
  public String filtrarPelicula(@RequestParam("id") int id, Model model) {
    Categoria c = categoriaService.findById(id);
    List<Pelicula> peliculas = peliculasService.findByCategoria(c);
    List<Categoria> categorias = categoriaService.findAll();
    model.addAttribute("peliculas", peliculas);
    model.addAttribute("categorias", categorias);
    return "peliculas/mostrarPeliculas";
  }

}
