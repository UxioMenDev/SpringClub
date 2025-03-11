package com.example.demo.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Configuration;

import com.example.demo.controllers.entities.Actor;
import com.example.demo.controllers.entities.Categoria;
import com.example.demo.controllers.entities.Director;
import com.example.demo.controllers.entities.Pelicula;
import com.example.demo.servicios.ActorService;
import com.example.demo.servicios.CategoriaService;
import com.example.demo.servicios.DirectorService;
import com.example.demo.servicios.PeliculasService;

import jakarta.annotation.PostConstruct;

@Configuration
public class DataInitializer {

  private PeliculasService peliculasService;
  private ActorService actorService;
  private DirectorService directorService;
  private CategoriaService categoriaService;

  public DataInitializer(PeliculasService peliculasService, ActorService actorService,
      DirectorService directorService, CategoriaService categoriaService) {
    this.peliculasService = peliculasService;
    this.actorService = actorService;
    this.directorService = directorService;
    this.categoriaService = categoriaService;

  }

  @PostConstruct
  public void init() {

    List<Pelicula> peliculas = peliculasService.findAll();
    System.out.println("Peliculas encontradas: " + peliculas.size());
    if (peliculas.isEmpty()) {
      System.out.println("No se encontraron películas, insertando datos de ejemplo...");

      Director directorEjemplo = new Director();
      directorEjemplo.setNombre("Akira Kurosawa");
      directorEjemplo.setRutaImagen("/images/director/director1.jpg");

      directorService.crear(directorEjemplo);
      System.out.println("Director creado: " + directorEjemplo.getNombre());

      Actor actorEjemplo1 = new Actor();
      actorEjemplo1.setNombre("Tatsuya Nakadai");
      actorEjemplo1.setRutaImagen("/images/actor/actor1.jpg");

      actorService.crear(actorEjemplo1);
      System.out.println("Actor creado: " + actorEjemplo1.getNombre());

      Actor actorEjemplo2 = new Actor();
      actorEjemplo2.setNombre("Toshiro Mifune");
      actorEjemplo2.setRutaImagen("/images/actor/actor2.jpg");

      actorService.crear(actorEjemplo2);
      System.out.println("Actor creado: " + actorEjemplo2.getNombre());

      Set<Actor> actores = new HashSet<Actor>();
      actores.add(actorEjemplo2);
      actores.add(actorEjemplo1);

      Pelicula peliculaEjemplo = new Pelicula();
      peliculaEjemplo.setTitulo("Yojimbo");
      peliculaEjemplo.setAnno(1961);
      peliculaEjemplo.setDirector(directorEjemplo);
      peliculaEjemplo.setActores(actores);

      peliculasService.crear(peliculaEjemplo);
      System.out.println("Pelicula creada: " + peliculaEjemplo.getTitulo());

      List<Categoria> listaCategorias = categoriaService.findAll();
      if (listaCategorias.isEmpty()) {
        String[] categorias = { "Acción", "Aventura", "Comedia", "Drama", "Terror", "Ciencia ficción", "Documental",
            "Romance", "Suspenso", "Animación", "Fantasía", "Musical" };
        for (String nombreCategoria : categorias) {
          Categoria categoria = new Categoria();
          categoria.setNombre(nombreCategoria);
          categoriaService.crear(categoria);
        }
      }
    }
  }
}