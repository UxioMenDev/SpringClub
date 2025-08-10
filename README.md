# SpringClub

[![Java](https://img.shields.io/badge/Java-21-red?logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.1-6DB33F?logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![JPA](https://img.shields.io/badge/JPA-3.1-orange?logo=hibernate&logoColor=white)](https://jakarta.ee/specifications/persistence/)
[![Hibernate](https://img.shields.io/badge/Hibernate-6.4-59666C?logo=hibernate&logoColor=white)](https://hibernate.org/)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6.2-6DB33F?logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![Lombok](https://img.shields.io/badge/Lombok-1.18-red?logo=lombok&logoColor=white)](https://projectlombok.org/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.2-005F0F?logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3.3-7952B3?logo=bootstrap&logoColor=white)](https://getbootstrap.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![jQuery](https://img.shields.io/badge/jQuery-3.7.1-0769AD?logo=jquery&logoColor=white)](https://jquery.com/)
[![Docker](https://img.shields.io/badge/Docker-24.0.9-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)

## Description
A Spring Boot web app for managing sport clubs

- Spring Security authentication
- Different user roles: user, admin and coach
- Create players and coaches and relate them to teams
- Creates Seasons automatically every year
- Automatically asigns players to teams based on their age and gender
- Filter teams by season and players by age
- PayPal integration


## Prerrequisites

### 🖥️ Local

- Java 21
- Maven
- PostgreSQL

## 🐳 Docker

- Docker
- Docker Compose

## Usage


1. Clone the repository:

   ```sh
   git clone https://github.com/UxioMenDev/SpringClub.git
   ```

### 🖥️ Local

1. Create database:

   ```mysql
   create database if not exists spring_club;
   ```

2. Compile and execute:
   ```sh
   ./mvnw spring-boot:run
   ```
   
## 🐳 Docker

1. Build the Docker image:
     ```
     docker compose build
     ```
2. Start the services:
     ```
     docker compose up
     ```

Log in with default administrator credentials:

  username:
  ````
  admin@admin
  ````
   password:
   ````
  admin
   ````

## 🌥️ Supported Cloud Providers

### AWS S3
- **Service**: Amazon S3
- **Bucket**
- **Features**: Standard S3 storage

### Azure Blob Storage
- **Service**: Azure Blob Storage
- Files served through Spring Boot application
- Secure access controlled by application

## 🔧 Environment Variables

```env
DATABASE_URL=jdbc:your-database-url
DATABASE_USERNAME=your-username
DATABASE_PASSWORD=your-password

### Storage Provider Selection
# Choose storage provider: azure or aws, local by default
STORAGE_PROVIDER=local

# AWS S3 Configuration 
AWS_REGION=your-region
AWS_S3_BUCKET_NAME=your-bucket-name
AWS_ACCESS_KEY_ID=your-access-key
AWS_SECRET_ACCESS_KEY=your-secret-key

# Azure Blob Storage Configuration 
AZURE_STORAGE_ACCOUNT_NAME=your-storage-account
AZURE_STORAGE_CONTAINER_NAME=media
AZURE_STORAGE_CONNECTION_STRING=DefaultEndpointsProtocol=https;AccountName=...
```



## Images
![image](https://github.com/user-attachments/assets/9b79800c-48ab-4137-9e3c-1b03059175af)
![image](https://github.com/user-attachments/assets/5ef47614-2ca9-4aca-8b0c-64986e993f31)
![image](https://github.com/user-attachments/assets/55ffe1fb-6261-494e-85b7-44ffb2cec664)
![image](https://github.com/user-attachments/assets/97df525d-42b7-40de-8c0e-63840b6e0356)

