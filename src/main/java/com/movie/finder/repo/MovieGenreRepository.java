package com.movie.finder.repo;

import com.movie.finder.model.Movie;
import com.movie.finder.model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre,Integer> {

    List<MovieGenre> findByMovie(Movie movie);
}
