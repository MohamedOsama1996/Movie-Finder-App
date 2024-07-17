package com.movie.finder.controller;

import com.movie.finder.documentation.DocumentationConstants;
import com.movie.finder.dto.MovieDto;
import com.movie.finder.model.User;
import com.movie.finder.model.request.UserMovieRatingRequest;
import com.movie.finder.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = DocumentationConstants.MovieControllerDescription.MOVIE_API,
        description = DocumentationConstants.MovieControllerDescription.MOVIE_DESCRIPTION)
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @Operation(summary = DocumentationConstants.MovieControllerDescription.GET_MOVIES_API,
            description = DocumentationConstants.MovieControllerDescription.GET_MOVIES_API_DESCRIPTION)
    @GetMapping
    public ResponseEntity<List<MovieDto>>getMovies(@RequestParam(name = "page",defaultValue = "1",required = false) int page){
        return ResponseEntity.ok().body(movieService.getMoviesByPage(page));
    }

    @Operation(summary = DocumentationConstants.MovieControllerDescription.POST_ADD_MOVIE_API,
            description = DocumentationConstants.MovieControllerDescription.POST_ADD_MOVIE_API_DESCRIPTION)
    @PostMapping("/{movieId}/favorites")
    public ResponseEntity<?> addUserFilmToFavorite(@PathVariable int movieId){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        movieService.AddMovieToFavorites(movieId,user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = DocumentationConstants.MovieControllerDescription.POST_ADD_MOVIE_API,
            description = DocumentationConstants.MovieControllerDescription.POST_ADD_MOVIE_API_DESCRIPTION)
    @GetMapping("/favorites")
    public ResponseEntity<List<MovieDto>> getUserFavorites(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok( movieService.getUserFavorites(user));
    }

    @Operation(summary = DocumentationConstants.MovieControllerDescription.POST_RATE_MOVIE_API,
            description = DocumentationConstants.MovieControllerDescription.POST_RATE_MOVIE_API_DESCRIPTION)
    @PostMapping("/{movieId}/rate")
    public ResponseEntity<?> userRateMovies(@PathVariable int movieId,@RequestBody UserMovieRatingRequest userMovieRatingRequest){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        movieService.userRateMovie(movieId,userMovieRatingRequest.getUserRating(),user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
