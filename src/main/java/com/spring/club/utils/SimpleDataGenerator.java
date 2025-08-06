package com.spring.club.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.club.entities.*;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;
import com.spring.club.repositories.*;
import com.spring.club.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SimpleDataGenerator implements CommandLineRunner {

    @Autowired private PlayerRepository playerRepository;
    @Autowired private CoachRepository coachRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private SeasonService seasonService;
    @Autowired private PlayerSeasonRepository playerSeasonRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        if (playerRepository.count() == 0 && coachRepository.count() == 0 && teamRepository.count() == 0) {
            System.out.println("🚀 Generando datos desde API...");


            // Obtener temporada activa
            Season season = seasonService.getCurrentSeason();
            if (season == null) return;

            String json = restTemplate.getForObject("https://api.generadordni.es/profiles/person", String.class);
            JsonNode root = mapper.readTree(json);
            List<JsonNode> profiles = new ArrayList<>();
            if (root.isArray()) {
                for (JsonNode persona : root) {
                    profiles.add(persona);
                }
            }

            // Crear entrenador
            createCoach(profiles.getFirst());

            // Crear equipos
            createTeams(season);

            // Crear jugadores
            List<Team> teams = teamRepository.findAll();
            for (int i = 1; i < 10; i++) {
                createPlayer(profiles.get(i), teams, season);
            }

            System.out.println("✅ Datos generados!");
        }
    }

    private JsonNode callAPI() {
        try {
            String response = restTemplate.getForObject("https://api.generadordni.es/profiles/person", String.class);
            return mapper.readTree(response);
        } catch (Exception e) {
            System.out.println("⚠️ API falló");
            return null;
        }
    }

    private void createCoach(JsonNode data) {
        try {
            Coach coach = new Coach();
            coach.setName(data.get("nombre_completo").asText());
            coachRepository.save(coach);
        } catch (Exception e) {
            System.err.println("Error creando entrenador: " + e.getMessage());
        }
    }

    private void createPlayer(JsonNode data, List<Team> teams, Season season) {
        try {
            Player player = new Player();
            player.setName(data.get("nombre_completo").asText());
            player.setIdCard(data.get("nif").asText());
            player.setLicenseNumber(data.get("ssn").asText());
            player.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").parse(data.get("fecha_nacimiento").asText()));
            player.setPlaceOfBirth(data.get("municipio").asText());
            player.setSex(data.get("sexo").asText().equals("hombre") ? Gender.MASCULINO : Gender.FEMENINO);
            player.setEmail(data.get("email").asText());
            player.setPhone(Integer.parseInt(data.get("telefono").asText()));
            player.setStreet(data.get("direccion").asText());
            player.setCity(data.get("municipio").asText());
            player.setZip(Integer.valueOf(data.get("codigo_postal").asText().replace("\"", "")));
            player.setCountry("España");
            player.setNationality("Española");

            // Calcular categoría por edad
            player.calculateCategory();


            player = playerRepository.save(player);

            // Asignar a equipo
            Player finalPlayer = player;
            Team team = teams.stream()
                .filter(t -> t.getCategory() == finalPlayer.getCategory() && t.getGender() == finalPlayer.getSex())
                .findFirst().orElse(null);

            if (team != null) {
                PlayerSeason ps = new PlayerSeason();
                ps.setPlayer(player);
                ps.setSeason(season);
                playerSeasonRepository.save(ps);
            }
        } catch (Exception e) {
            System.err.println("Error creando jugador: " + e.getMessage());
        }
    }

    private void createTeams(Season season) {
        List<Coach> coaches = coachRepository.findAll();
        int coachIndex = 0;

        for (Category category : Category.values()) {
            for (Gender gender : Gender.values()) {
                Team team = new Team();
                team.setCategory(category);
                team.setGender(gender);
                team.setSeason(season);
                if (!coaches.isEmpty()) {
                    team.setCoach(coaches.get(coachIndex % coaches.size()));
                    coachIndex++;
                }
                teamRepository.save(team);
            }
        }
    }
}
