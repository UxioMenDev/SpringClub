package com.spring.club.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.spring.club.entities.Country;
import com.spring.club.entities.Season;
import com.spring.club.entities.Team;
import com.spring.club.entities.enums.Category;
import com.spring.club.services.CountryService;
import com.spring.club.services.PlayerService;
import com.spring.club.services.SeasonService;
import com.spring.club.services.TeamService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.club.entities.Player;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final CountryService countryService;
    private final SeasonService seasonService;
    private final TeamService teamService;

    public PlayerController(PlayerService playerService, CountryService countryService, SeasonService seasonService, TeamService teamService) {
        this.playerService = playerService;
        this.countryService = countryService;
        this.seasonService = seasonService;
        this.teamService = teamService;
    }

    @GetMapping("form")
    public String showForm(@ModelAttribute Player p, Model model) {
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        Season currentSeason = seasonService.getCurrentSeason();
        p.setSeasons(Set.of(currentSeason));
        model.addAttribute("currentSeason", currentSeason);
        return "players/formPlayer";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Player p) throws IOException {
        p.calculateCategory();
        MultipartFile image = p.getImage();
        if (!image.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/images/player/" + fileName);
            Files.copy(image.getInputStream(), path);
            p.setImagePath("/images/player/" + fileName);
            System.out.println(p.getImagePath());
        }
        playerService.create(p);
        assignToTeam(p);
        return "redirect:/player/list";
    }

    @GetMapping("list")
    public String showPlayers(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        List<Player> players = playerService.findAll();
        model.addAttribute("players", players);
        return "players/showPlayers";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("id") int id) {
        Player p = playerService.findById(id);
        try {
            playerService.delete(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/player/list";
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") int id, Model model) throws IOException {
        Player p = playerService.findById(id);
        MultipartFile image = p.getImage();
        if (image != null && !image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/images/player/" + fileName);
            Files.copy(image.getInputStream(), path);
            p.setImagePath("/images/player/" + fileName);
        }
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("player", p);
        model.addAttribute("currentSeason", seasonService.getCurrentSeason());
        return "players/formPlayer";
    }

    @GetMapping("/filter")
    public String filterByCategory(@RequestParam("category") Category category, Model model, HttpServletRequest request) {
        Long season_id = seasonService.getCurrentSeason().getId();
        List<Player> filteredPlayers = playerService.findByCategoryAndSeason(season_id, category);
        model.addAttribute("players", filteredPlayers);
        model.addAttribute("currentURI", request.getRequestURI());
        return "players/kanbanPlayers";
    }

    @PostMapping("/renovate")
    public String batchAction(@RequestParam(value = "selectedIds", required = false) List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            playerService.renovate(ids);
        }
        return "redirect:/player/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ModelAttribute("seasons")
    public List<Season> getAllSeasons() {
        return seasonService.findAll();
    }


    private void assignToTeam(Player player) {
        Season currentSeason = seasonService.getCurrentSeason();

        List<Team> teams = teamService.findBySeasonAndCategoryAndGender(
                currentSeason,
                player.getCategory(),
                player.getSex()
        );

        if (!teams.isEmpty()) {
            Team team = teams.get(0);
            team.getPlayers().add(player);
            teamService.create(team);
        }
    }


}