package com.movie.finder.mapper;

import com.movie.finder.client.MovieClient;
import com.movie.finder.client.response.ClientMovie;
import com.movie.finder.dto.MovieDto;
import com.movie.finder.model.Movie;
import com.movie.finder.model.MovieGenre;
import com.movie.finder.repo.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MovieMapper {


    @Autowired
    MovieGenreRepository movieGenreRepository;


    public  MovieDto EntityToDto(Movie movie){

        List<MovieGenre> movieGenres = movieGenreRepository.findByMovie(movie);

        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setOriginalLanguage(movie.getLang());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setPopularity(movie.getPopularity());
        movieDto.setOriginalTitle(movie.getTitle());
        movieDto.setGenres(movieGenres.stream().map(MovieGenre::getGenre).toList());
        movieDto.setAverageRating(movie.getAverageRating());
        return movieDto;
    }


    public  Movie ToEntity(ClientMovie clientMovie){

        Movie movie = new Movie();
        movie.setTmdbId(clientMovie.getTmdbId());
        movie.setLang(clientMovie.getOriginalLanguage());
        movie.setTitle(clientMovie.getOriginalTitle());
        movie.setReleaseDate(clientMovie.getReleaseDate());
        movie.setPopularity(clientMovie.getPopularity());
        return movie;
    }

}
