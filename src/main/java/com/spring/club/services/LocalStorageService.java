package com.spring.club.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@ConditionalOnProperty(name = "storage.provider", havingValue = "local", matchIfMissing = true)
public class LocalStorageService implements StorageService {

    @Value("${storage.local.path:uploads}")
    private String localStoragePath;

    @Value("${server.port:8080}")
    private String serverPort;

    @Override
    public String uploadFile(String key, byte[] fileContent) {
        try {
            // Crear el directorio si no existe
            Path uploadDir = Paths.get(localStoragePath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Guardar el archivo
            Path filePath = uploadDir.resolve(key);
            Files.write(filePath, fileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            return getPublicUrl(key);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo localmente: " + e.getMessage(), e);
        }
    }

    @Override
    public String getPublicUrl(String key) {
        return "http://localhost:" + serverPort + "/files/" + key;
    }

    @Override
    public void deleteFile(String key) {
        try {
            Path filePath = Paths.get(localStoragePath).resolve(key);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar archivo: " + e.getMessage(), e);
        }
    }
}
