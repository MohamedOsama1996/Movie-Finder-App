package com.movie.finder.repo;

import com.movie.finder.model.Movie;
import com.movie.finder.model.User;
import com.movie.finder.model.UserMovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating,Integer> {


   Optional<UserMovieRating> findByUserAndMovie(User user, Movie movie);
}
