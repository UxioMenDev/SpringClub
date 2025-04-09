# SpringClub
## Description
A Spring Boot web app for managing sport clubs

- Spring Security authentication
- Create players and coaches and relate them to teams
- Creates Seasons automatically
- Automatically asigns players to teams based on their age and gender
- Filter teams by season and players by age

## Technologies

- Java 21
- Spring Boot 3.4.1
- JPA
- Spring Security
- Lombok
- Thymeleaf
- Bootstrap 5.3.3
- MySQL
- Maven

## Configuration

### Prerrequisites

- Java 21
- Maven
- MySQL

### Installation and usage

1. Clone the repository:

   ```sh
   git clone https://github.com/UxioMenDev/SpringClub.git
   ```

2. Create database:

   ```mysql
   create database if not exists spring_club;
   ```

3. Compile and execute:
   ```sh
   ./mvnw spring-boot:run
   ```
4. Log in with default administrator credentials:
   - username: admin@admin
   - password: admin

