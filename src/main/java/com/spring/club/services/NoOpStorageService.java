package com.spring.club.services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "storage.provider", havingValue = "none")
public class NoOpStorageService implements StorageService {

    @Override
    public String uploadFile(String key, byte[] fileContent) {
        // No hace nada, solo devuelve una URL ficticia
        return "http://localhost/files/" + key;
    }

    @Override
    public String getPublicUrl(String key) {
        return "http://localhost/files/" + key;
    }

    @Override
    public void deleteFile(String key) {
        // No hace nada
    }
}
