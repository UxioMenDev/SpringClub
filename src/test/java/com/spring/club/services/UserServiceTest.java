package com.spring.club.services;

import com.spring.club.entities.User;
import com.spring.club.entities.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired private UserService userService;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUsername("usuario");
        user.setPassword("testpassword");
        user.setRoles(new java.util.HashSet<>(Set.of(Role.ROLE_USER)));
        userService.create(user);

        assertThat(user.getId()).isNotNull();
        assertThat(userService.findByUsername("usuario")).isNotNull();
    }
}