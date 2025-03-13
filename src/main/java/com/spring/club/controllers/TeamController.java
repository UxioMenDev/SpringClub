package com.spring.club.controllers;

import java.util.List;

import com.spring.club.services.PlayerService;
import com.spring.club.services.CoachService;
import com.spring.club.services.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.spring.club.controllers.entities.Player;
import com.spring.club.controllers.entities.Coach;
import com.spring.club.controllers.entities.Team;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private TeamService teamService;
    private CoachService coachService;
    private PlayerService playerService;

    public TeamController(TeamService teamService, CoachService coachService, PlayerService playerService) {
        this.teamService = teamService;
        this.coachService = coachService;
        this.playerService = playerService;
    }

    @GetMapping("form")
    public String showForm(@ModelAttribute Team t, Model model) {
        List<Coach> coaches = coachService.findAll();
        model.addAttribute("coaches", coaches);
        List<Player> players = playerService.findAll();
        model.addAttribute("players", players);
        return "teams/formTeam";
    }

    @PostMapping("create")
    public String create(@ModelAttribute Team t) {
        teamService.create(t);
        return "redirect:/teams/list";
    }

    @GetMapping("list")
    public String showTeams(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        List<Team> teams = teamService.findAll();
        model.addAttribute("teams", teams);
        return "teams/showTeams";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("id") int id) {
        Team t = teamService.findById(id);
        teamService.delete(t);
        return "redirect:/teams/list";
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") int id, Model model) {
        Team t = teamService.findById(id);
        model.addAttribute("team", t);
        List<Coach> coaches = coachService.findAll();
        model.addAttribute("coaches", coaches);
        List<Player> players = playerService.findAll();
        model.addAttribute("players", players);
        return "teams/formTeam";
    }

}