package com.spring.club.services;

public interface StorageService {
    String uploadFile(String key, byte[] fileContent);
    String getPublicUrl(String key);
    void deleteFile(String key);
}
