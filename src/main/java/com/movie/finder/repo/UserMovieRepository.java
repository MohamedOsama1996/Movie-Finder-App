package com.movie.finder.repo;

import com.movie.finder.model.MovieGenre;
import com.movie.finder.model.User;
import com.movie.finder.model.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie,Long> {

    List<UserMovie> findByUser(User user);
}
