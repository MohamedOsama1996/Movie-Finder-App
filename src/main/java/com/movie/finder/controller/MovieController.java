package com.movie.finder.controller;

import com.movie.finder.dto.MovieDto;
import com.movie.finder.model.User;
import com.movie.finder.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{movieId}/favorites")
    public ResponseEntity<?> addUserFilmToFavorite(@PathVariable int movieId){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        movieService.AddMovieToFavorites(movieId,user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<MovieDto>> getUserFavorites(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok( movieService.getUserFavorites(user));
    }
}
