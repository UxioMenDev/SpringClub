package com.spring.club.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<PlayerSeason> playerSeasons = new HashSet<>();
    @Column(name = "license_number")
    private String licenseNumber;

    public void calculateCategory() {
        if (this.birthdate == null) return;

        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(this.birthdate);

        Calendar referenceDate = Calendar.getInstance();
        referenceDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
        referenceDate.set(Calendar.DAY_OF_MONTH, 30);

        int age = referenceDate.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (referenceDate.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        if (age >= 19) {
            this.category = Category.ABSOLUTO;
        } else if (age >= 17) {
            this.category = Category.JUVENIL;
        } else if (age >= 15) {
            this.category = Category.CADETE;
        } else if (age >= 13) {
            this.category = Category.INFANTIL;
        } else {
            this.category = Category.ALEVIN;
        }
}}