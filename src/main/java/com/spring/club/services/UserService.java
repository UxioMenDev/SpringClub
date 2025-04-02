package com.spring.club.services;

import com.spring.club.entities.User;

import java.util.List;

public interface UserService {
    void create(User u);
    User findByUsername(String username);
    List<User> findAll();

}