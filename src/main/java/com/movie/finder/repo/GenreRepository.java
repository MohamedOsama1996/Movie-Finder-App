package com.movie.finder.repo;

import com.movie.finder.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Integer> {

    Optional<Genre> findByGenreName(String genre);
}
