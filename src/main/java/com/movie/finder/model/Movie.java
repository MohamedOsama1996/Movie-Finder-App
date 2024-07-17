package com.movie.finder.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int tmdbId;

    private String title;

    private String lang;

    private String releaseDate;

    private int ratingCount;

    private double averageRating;

    private double totalRating;

    public Movie(int id) {
        this.id = id;
    }
    public Movie(){}

    private double popularity;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private List<MovieGenre> movieGenres;

}
