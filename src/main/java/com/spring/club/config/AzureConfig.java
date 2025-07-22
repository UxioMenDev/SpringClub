package com.spring.club.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureConfig {

    @Value("${azure.storage.connection-string:}")
    private String connectionString;

    @Value("${azure.storage.account-name:}")
    private String accountName;

    @Value("${azure.storage.account-key:}")
    private String accountKey;

    @Value("${azure.storage.endpoint:}")
    private String endpoint;

    @Bean
    @ConditionalOnProperty(name = "storage.provider", havingValue = "azure")
    public BlobServiceClient blobServiceClient() {
        // Verificar que tenemos configuración válida antes de crear el cliente
        boolean hasConnectionString = connectionString != null && !connectionString.trim().isEmpty();
        boolean hasAccountCredentials = (accountName != null && !accountName.trim().isEmpty() &&
                                       accountKey != null && !accountKey.trim().isEmpty());

        if (!hasConnectionString && !hasAccountCredentials) {
            throw new IllegalStateException(
                "Azure Blob Storage configuration incomplete. " +
                "Provide either 'azure.storage.connection-string' or both " +
                "'azure.storage.account-name' and 'azure.storage.account-key'"
            );
        }

        // Priorizar connection string si está disponible
        if (hasConnectionString) {
            return new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
        }

        // Si no hay connection string, usar account name y key
        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
        String blobEndpoint = endpoint.isEmpty() ?
            String.format("https://%s.blob.core.windows.net", accountName) : endpoint;

        return new BlobServiceClientBuilder()
                .endpoint(blobEndpoint)
                .credential(credential)
                .buildClient();
    }
}
