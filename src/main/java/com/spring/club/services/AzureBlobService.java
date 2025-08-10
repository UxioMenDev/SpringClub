package com.spring.club.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@ConditionalOnProperty(name = "storage.provider", havingValue = "azure")
public class AzureBlobService implements StorageService {

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Value("${azure.storage.account-name}")
    private String accountName;

    private final BlobServiceClient blobServiceClient;

    public AzureBlobService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    @Override
    public String uploadFile(String fileName, byte[] fileContent) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        // Crear el contenedor si no existe
        if (!containerClient.exists()) {
            containerClient.create();
        }

        BlobClient blobClient = containerClient.getBlobClient(fileName);

        // Subir el archivo
        blobClient.upload(new ByteArrayInputStream(fileContent), fileContent.length, true);

        return getPublicUrl(fileName);
    }

    @Override
    public String getPublicUrl(String fileName) {
        return "/files/" + fileName;
    }

    @Override
    public void deleteFile(String fileName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        if (blobClient.exists()) {
            blobClient.delete();
        }
    }
}
