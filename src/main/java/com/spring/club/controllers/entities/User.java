package com.spring.club.controllers.entities;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private Collection<GrantedAuthority> roles;
    @OneToMany
    private Set<Player> players;

}