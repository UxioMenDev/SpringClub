package com.spring.club.entities;

import java.util.Date;
import java.util.Set;

import com.spring.club.entities.enums.Gender;
import com.spring.club.entities.enums.Category;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "player_team",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )    private Set<Team> teams;
    @Transient
    private MultipartFile image;
    private String imagePath;
    @Column(nullable = false)
    private String idCard;
    @Column(nullable = false)
    private Integer phone;
    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender sex;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birthdate;
    @Column(nullable = false)
    private String street;
    private String city;
    @Column(nullable = false)
    private Integer zip;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Country country;
    @ManyToOne
    private Country nationality;
    @Column(nullable = false)
    private String placeOfBirth;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany
    @JoinTable(
            name = "player_season",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "season_id")
    )
    private Set<Season> seasons;
    @Column(name = "license_number")
    private String licenseNumber;
}