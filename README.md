# SpringClub
## Description
A Spring Boot web app for managing sport clubs

- Spring Security authentication
- Different user roles: user, admin and coach
- Create players and coaches and relate them to teams
- Creates Seasons automatically every year
- Automatically asigns players to teams based on their age and gender
- Filter teams by season and players by age
- PayPal integration

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
- jQuery 3.7.1

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

## Images
![image](https://github.com/user-attachments/assets/9b79800c-48ab-4137-9e3c-1b03059175af)
![image](https://github.com/user-attachments/assets/5ef47614-2ca9-4aca-8b0c-64986e993f31)
![image](https://github.com/user-attachments/assets/55ffe1fb-6261-494e-85b7-44ffb2cec664)
![image](https://github.com/user-attachments/assets/97df525d-42b7-40de-8c0e-63840b6e0356)

