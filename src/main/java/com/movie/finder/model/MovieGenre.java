package com.movie.finder.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "movies_genres")
public class MovieGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_genre_id")
    private int movieGenreId;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public MovieGenre() {}

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;


    public MovieGenre(Movie movie, Genre genre) {
        this.movie = movie;
        this.genre = genre;
    }
}
