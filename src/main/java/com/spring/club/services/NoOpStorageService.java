package com.spring.club.services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "storage.provider", havingValue = "none", matchIfMissing = true)
public class NoOpStorageService implements StorageService {

    @Override
    public String uploadFile(String key, byte[] fileContent) {
        // No hacer nada, solo retornar una URL de placeholder
        return "/images/placeholder.jpg";
    }

    @Override
    public String getPublicUrl(String key) {
        return "/images/placeholder.jpg";
    }

    @Override
    public void deleteFile(String key) {
        // No hacer nada
    }
}
