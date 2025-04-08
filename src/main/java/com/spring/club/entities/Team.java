package com.spring.club.entities;

import java.util.HashSet;
import java.util.Set;

import com.spring.club.entities.enums.Category;
import com.spring.club.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"category", "gender", "season_id"})
})
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Category category;
    private Gender gender;
    @ManyToOne
    private Season season;
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "player_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> players = new HashSet<>();


}