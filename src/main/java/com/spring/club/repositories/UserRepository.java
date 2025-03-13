package com.spring.club.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.club.controllers.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String email);
}