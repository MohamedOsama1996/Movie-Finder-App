package com.movie.finder.controller;

import com.movie.finder.dto.MovieDto;
import com.movie.finder.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDto>>getMovies(@RequestParam(name = "page",defaultValue = "1",required = false) int page){

        return ResponseEntity.ok().body(movieService.getMoviesByPage(page));
    }
}
