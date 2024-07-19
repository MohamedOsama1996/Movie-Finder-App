package com.movie.finder.service;

import com.movie.finder.dto.MovieDto;
import com.movie.finder.model.User;

import java.util.List;

public interface MovieService {


    List<MovieDto> getMoviesByPage(int page);

    void addMovieToFavorites(int movieId, User user);

    List<MovieDto>getUserFavorites(User user);

    void userRateMovie(int movieId,double userRating,User user);

    List<MovieDto> getMoviesByGenre(String genre,int page);
}
