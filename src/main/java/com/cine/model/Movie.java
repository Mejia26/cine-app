package com.cine.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "movies")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String director;

    private String genre;

    private LocalDate releaseDate;

    private double rating; // de 0 a 10

    // === Constructors ===
    public Movie() {
    }

}
