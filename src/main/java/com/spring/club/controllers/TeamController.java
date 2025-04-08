package com.spring.club.controllers;

import java.util.List;

import com.spring.club.entities.Season;
import com.spring.club.services.PlayerService;
import com.spring.club.services.CoachService;
import com.spring.club.services.SeasonService;
import com.spring.club.services.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.spring.club.entities.Player;
import com.spring.club.entities.Coach;
import com.spring.club.entities.Team;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private TeamService teamService;
    private CoachService coachService;
    private PlayerService playerService;
    private final SeasonService seasonService;


    public TeamController(TeamService teamService, CoachService coachService, PlayerService playerService, SeasonService seasonService) {
        this.teamService = teamService;
        this.coachService = coachService;
        this.playerService = playerService;
        this.seasonService = seasonService;
    }

    @GetMapping("form")
    public String showForm(@ModelAttribute Team t, Model model) {
        Team team = new Team();
        team.setSeason(seasonService.getCurrentSeason());
        model.addAttribute("team", team);
        List<Coach> coaches = coachService.findAll();
        model.addAttribute("coaches", coaches);
        return "teams/formTeam";
    }

    @PostMapping("create")
    public String create(@ModelAttribute Team t, Model model) {
        try {
            teamService.create(t);
            return "redirect:/teams/list";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("team", t);
            model.addAttribute("coaches", coachService.findAll());
            Long season_id = seasonService.getCurrentSeason().getId();
            List<Player> players = playerService.findByCategoryAndGenderIntersection(season_id, t.getCategory(), t.getGender());
            model.addAttribute("players", players);
            return "teams/formTeam";
        }
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
        Long season_id = seasonService.getCurrentSeason().getId();
        Team t = teamService.findById(id);
        model.addAttribute("team", t);
        List<Coach> coaches = coachService.findAll();
        model.addAttribute("coaches", coaches);
        List<Player> players = playerService.findByCategoryAndGenderIntersection(season_id, t.getCategory(), t.getGender());
        model.addAttribute("players", players);
        return "teams/formTeam";
    }



    @GetMapping("filter")
    public String filterBySeasons(@RequestParam("season") Long seasonId, Model model) {
        List<Team> teams = teamService.findBySeasonId(seasonId);
        model.addAttribute("teams", teams);
        return "teams/showTeams";
    }

    @ModelAttribute("seasons")
    public List<Season> getAllSeasons() {
        return seasonService.findAll();
    }



}