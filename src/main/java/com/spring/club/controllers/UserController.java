package com.spring.club.controllers;

import com.spring.club.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.club.entities.User;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(@ModelAttribute User u) {
        return "users/register";
    }


    @PostMapping("/add")
    public String save(@ModelAttribute User u) {
        userService.create(u);
        return "users/login";
    }

    @GetMapping("/login")
    public String showLogin(@ModelAttribute User u) {
        return "users/login";
    }

}