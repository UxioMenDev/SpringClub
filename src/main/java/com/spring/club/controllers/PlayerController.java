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

import com.spring.club.entities.*;
import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Role;
import com.spring.club.services.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final CountryService countryService;
    private final SeasonService seasonService;
    private final TeamService teamService;
    private final UserService userService;

    public PlayerController(PlayerService playerService, CountryService countryService, SeasonService seasonService, TeamService teamService, UserService userService) {
        this.playerService = playerService;
        this.countryService = countryService;
        this.seasonService = seasonService;
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping("form")
    public String showForm(@ModelAttribute Player p, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        Season currentSeason = seasonService.getCurrentSeason();
        model.addAttribute("currentSeason", currentSeason);
        model.addAttribute("paid", false);

        User currentUser = userService.findByUsername(userDetails.getUsername());
        boolean isAdmin = currentUser.getRoles().contains(Role.ROLE_ADMIN);

        if (isAdmin) {
            List<User> users = userService.findAll();
            model.addAttribute("users", users);
        }
        model.addAttribute("isAdmin", isAdmin);
        return "players/formPlayer";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Player p, @RequestParam(defaultValue = "false") boolean paid, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        p.calculateCategory();
        MultipartFile image = p.getImage();
        if (!image.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/images/player/" + fileName);
            Files.copy(image.getInputStream(), path);
            p.setImagePath("/images/player/" + fileName);
        }
        User currentUser = userService.findByUsername(userDetails.getUsername());
        if (!currentUser.getRoles().contains(Role.ROLE_ADMIN)) {
            playerService.create(p, userDetails.getUsername(), paid);
        } else {
            playerService.create(p, p.getUser().getUsername(), paid);
        }
        assignToTeam(p);
        return "redirect:/player/list";
    }

    @GetMapping("list")
    public String showPlayers(
            @RequestParam(required = false) Long season,
            HttpServletRequest request,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        List<Player> players;
        User currentUser = userService.findByUsername(userDetails.getUsername());
        boolean isAdminOrCoach = currentUser.getRoles().contains(Role.ROLE_ADMIN) ||
                currentUser.getRoles().contains(Role.ROLE_COACH);

        if (!isAdminOrCoach) {
            players = season != null ?
                    playerService.findByUserAndSeason(currentUser, season) :
                    playerService.findByUser(currentUser);
        } else {
            players = season != null ?
                    playerService.findBySeason(season) :
                    playerService.findAll();
        }

        model.addAttribute("currentURI", request.getRequestURI());
        model.addAttribute("players", players);
        model.addAttribute("seasons", seasonService.findAll());
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
    public String edit(@RequestParam("id") int id, Model model, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Player p = playerService.findById(id);
        MultipartFile image = p.getImage();
        if (image != null && !image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/images/player/" + fileName);
            Files.copy(image.getInputStream(), path);
            p.setImagePath("/images/player/" + fileName);
        }
        List<Country> countries = countryService.findAll();
        List<User> users = userService.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("users", users);
        model.addAttribute("player", p);
        model.addAttribute("currentSeason", seasonService.getCurrentSeason());

        User currentUser = userService.findByUsername(userDetails.getUsername());
        boolean isAdmin = currentUser.getRoles().contains(Role.ROLE_ADMIN);
        model.addAttribute("isAdmin", isAdmin);

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