package com.spring.club.services;

import com.spring.club.entities.User;

public interface UserService {
    void create(User u);
    User findByUsername(String username);
}