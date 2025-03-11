package com.example.demo.servicios;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.controllers.entities.Usuario;
import com.example.demo.repositorios.UsuarioRepositorio;



@Service
public class UsuariosServiceImpl implements UsuariosService, UserDetailsService {


  private UsuarioRepositorio usuarioRepositorio;
  private PasswordEncoder passwordEncoder;

  public UsuariosServiceImpl(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
    this.usuarioRepositorio = usuarioRepositorio;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void crear(Usuario u) {
    u.setPassword(passwordEncoder.encode(u.getPassword()));
    Collection<GrantedAuthority> roles = new ArrayList<>();
    roles.add(new SimpleGrantedAuthority("ROLE_USER"));
    u.setRoles(roles);
    usuarioRepositorio.save(u);
  }

  @Override
  public Usuario buscarPorEmail(String email) {
    Usuario u = usuarioRepositorio.findByUsername(email);
    return u;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario user = usuarioRepositorio.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.getRoles());
  }

}
