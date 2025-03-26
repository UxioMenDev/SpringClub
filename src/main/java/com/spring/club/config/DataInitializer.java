package com.spring.club.config;

import java.time.LocalDateTime;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.List;

import com.spring.club.entities.Country;
import com.spring.club.entities.User;
import com.spring.club.entities.enums.Role;
import com.spring.club.repositories.CountryRepository;
import com.spring.club.repositories.SeasonRepository;
import com.spring.club.repositories.UserRepository;
import com.spring.club.services.SeasonService;
import com.spring.club.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CountryRepository countryRepository, SeasonService seasonService, SeasonRepository seasonRepository, UserService userService, PasswordEncoder passwordEncoder) {        return args -> {

            if (seasonRepository.count() ==0){
                    seasonService.getCurrentSeason();
                }

            if (userService.findByUsername("admin@admin") == null) {
                User adminUser = new User();
                adminUser.setUsername("admin@admin");
                adminUser.setPassword("admin");
                adminUser.getRoles().add(Role.ROLE_ADMIN);
                userService.create(adminUser);
            }


            if (countryRepository.count() == 0) {
                List<String> countries = Arrays.asList(
                        "Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
                        "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan",
                        "Bahamas", "Bahrain", "Bangladesh", "Belgium", "Belize",
                        "Brazil", "Bulgaria", "Cambodia", "Cameroon", "Canada",
                        "Chile", "China", "Colombia", "Croatia", "Cuba",
                        "Denmark", "Ecuador", "Egypt", "Estonia", "Ethiopia",
                        "Finland", "France", "Georgia", "Germany", "Greece",
                        "Hungary", "Iceland", "India", "Indonesia", "Iran",
                        "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
                        "Japan", "Kazakhstan", "Kenya", "Kuwait", "Latvia",
                        "Lebanon", "Libya", "Lithuania", "Luxembourg", "Malaysia",
                        "Mexico", "Mongolia", "Morocco", "Netherlands", "New Zealand",
                        "Nigeria", "Norway", "Pakistan", "Panama", "Peru",
                        "Philippines", "Poland", "Portugal", "Qatar", "Romania",
                        "Russia", "Saudi Arabia", "Serbia", "Singapore", "Slovakia",
                        "Slovenia", "South Africa", "South Korea", "Spain", "Sweden",
                        "Switzerland", "Syria", "Taiwan", "Thailand", "Turkey",
                        "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay",
                        "Venezuela", "Vietnam", "Yemen", "Zimbabwe"
                );

                countries.forEach(countryName -> {
                    Country country = new Country();
                    country.setName(countryName);
                    countryRepository.save(country);
                });
            }
        };
    }

}