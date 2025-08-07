package com.spring.club.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.club.entities.*;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;
import com.spring.club.repositories.*;
import com.spring.club.services.PlayerService;
import com.spring.club.services.SeasonService;
import com.spring.club.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
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
    @Autowired private StorageService storageService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();
    @Autowired
    private PlayerService playerService;

    @Override
    public void run(String... args) throws Exception {
        if (playerRepository.count() == 0 && coachRepository.count() == 0 && teamRepository.count() == 0) {
            System.out.println("🚀 Generando datos...");

            // Obtener temporada activa
            Season season = seasonService.getCurrentSeason();
            if (season == null) return;

            List<JsonNode> profiles = new ArrayList<>();

            try {
                // Intentar obtener datos de la API
                String json = restTemplate.getForObject("https://api.generadordni.es/profiles/person", String.class);
                JsonNode root = mapper.readTree(json);

                if (root.isArray()) {
                    for (JsonNode persona : root) {
                        profiles.add(persona);
                    }
                }
                System.out.println("✅ Datos obtenidos de la API: " + profiles.size() + " perfiles");
            } catch (Exception e) {
                System.out.println("⚠️ Error al obtener datos de la API: " + e.getMessage());

            }

            // Verificar que tenemos datos antes de continuar
            if (profiles.isEmpty()) {
                System.err.println("❌ No se pudieron obtener datos ni de la API ni mock. Abortando generación.");
                return;
            }

            // Crear entrenador
            createCoach(profiles.get(0));

            // Crear equipos
            createTeams(season);

            // Crear jugadores
            List<Team> teams = teamRepository.findAll();
            for (int i = 1; i < Math.min(profiles.size(), 10); i++) {
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
            player.setNationality("España");
            PlayerSeason playerSeason = new PlayerSeason();
            playerSeason.setPlayer(player);
            playerSeason.setSeason(season);
            Set playerSeasons = new HashSet();
            playerSeasons.add(playerSeason);
            player.setPlayerSeasons(playerSeasons);

            // Descargar y guardar imagen
            try {
                MultipartFile imageFile = convertUrlToMultipartFile("https://thispersondoesnotexist.com/");
                String fileName = "player_" + System.currentTimeMillis() + "_" + random.nextInt(1000) + ".jpg";
                String imageUrl = storageService.uploadFile("players/" + fileName, imageFile.getBytes());
                player.setImagePath(imageUrl);
                System.out.println("✅ Imagen guardada para " + player.getName() + ": " + imageUrl);
            } catch (Exception e) {
                System.err.println("⚠️ Error descargando imagen para " + player.getName() + ": " + e.getMessage());
                player.setImagePath(null);
            }

            player.calculateCategory();
            player = playerRepository.save(player);

            playerService.assignToTeam(player);

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

    public static MultipartFile convertUrlToMultipartFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        String contentType = connection.getContentType();
        if (contentType == null) contentType = "image/jpeg";
        String fileName = "avatar.jpg";

        try (InputStream inputStream = url.openStream()) {
            byte[] fileBytes = inputStream.readAllBytes();
            return new MockMultipartFile(fileName, fileName, contentType, fileBytes);
        }
    }




}
