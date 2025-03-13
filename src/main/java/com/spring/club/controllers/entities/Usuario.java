package com.spring.club.controllers.entities;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String username;
  private String password;
  private Collection<GrantedAuthority> roles;
}
