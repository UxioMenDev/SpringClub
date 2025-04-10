package com.spring.club.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.spring.club.entities.Season;
import com.spring.club.services.CoachService;
import com.spring.club.services.SeasonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.club.entities.Coach;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/coach")
public class CoachController {

    private CoachService coachService;
    private SeasonService seasonService;

    public CoachController(CoachService coachService, SeasonService seasonService)
    {
        this.coachService = coachService;
        this.seasonService = seasonService;
    }

    @GetMapping("form")
    public String showForm(@ModelAttribute Coach c, Model model) {
        return "coaches/formCoach";
    }

    @PostMapping("create")
    public String create(@ModelAttribute Coach c) throws IOException {
        coachService.create(c);
        return "redirect:/coach/list";
    }

    @GetMapping("list")
    public String showCoaches(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        List<Coach> coaches = coachService.findAll();
        model.addAttribute("coaches", coaches);
        return "coaches/showCoaches";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("id") int id) {
        Coach c = coachService.findById(id);
        try {
            coachService.delete(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/coach/list";
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") int id, Model model) throws IOException {
        Coach c = coachService.findById(id);
        model.addAttribute("coach", c);
        return "coaches/formCoach";
    }

    @ModelAttribute("seasons")
    public List<Season> getAllSeasons() {
        return seasonService.findAll();
    }


}