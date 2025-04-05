package com.spring.club.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlayerSeason {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "player_id")
        private Player player;

        @ManyToOne
        @JoinColumn(name = "season_id")
        private Season season;

        private boolean paid = false;

    }

