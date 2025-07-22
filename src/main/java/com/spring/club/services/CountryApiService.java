package com.spring.club.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CountryApiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<String> fetchCountriesFromApi() {
        try {
            // Modificamos la URL para incluir también las traducciones
            String url = "https://restcountries.com/v3.1/all?fields=name,translations";
            String response = restTemplate.getForObject(url, String.class);

            JsonNode jsonNode = objectMapper.readTree(response);
            List<String> countries = new ArrayList<>();

            for (JsonNode countryNode : jsonNode) {
                String countryName;

                // Intentar obtener el nombre en español desde las traducciones
                JsonNode translations = countryNode.get("translations");
                if (translations != null && translations.has("spa")) {
                    // "spa" es el código para español en la API
                    JsonNode spanishTranslation = translations.get("spa");
                    if (spanishTranslation != null && spanishTranslation.has("common")) {
                        countryName = spanishTranslation.get("common").asText();
                    } else {
                        // Fallback al nombre común en inglés
                        countryName = countryNode.get("name").get("common").asText();
                    }
                } else {
                    // Fallback al nombre común en inglés si no hay traducción
                    countryName = countryNode.get("name").get("common").asText();
                }

                countries.add(countryName);
            }

            countries.sort(String::compareToIgnoreCase);
            return countries;

        } catch (Exception e) {
            System.err.println("Error fetching countries from the API: " + e.getMessage());
        }
        return null;
    }

}