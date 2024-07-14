package com.movie.finder.service;

import com.movie.finder.dto.MovieDto;

import java.util.List;

public interface MovieService {


    List<MovieDto> getMoviesByPage(int page);
}
