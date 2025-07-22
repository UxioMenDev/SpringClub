package com.spring.club.config;

import com.spring.club.entities.User;
import com.spring.club.entities.enums.Role;
import com.spring.club.repositories.SeasonRepository;
import com.spring.club.services.SeasonService;
import com.spring.club.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(SeasonService seasonService, SeasonRepository seasonRepository, UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {

            if (seasonRepository.count() == 0) {
                seasonService.getCurrentSeason();
            }

            if (userService.findByUsername("admin@admin") == null) {
                User adminUser = new User();
                adminUser.setUsername("admin@admin");
                adminUser.setPassword("admin");
                adminUser.getRoles().add(Role.ROLE_ADMIN);
                userService.create(adminUser);
            }

        };
    }
}