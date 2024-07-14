package com.movie.finder.scheduler;


import com.movie.finder.client.MovieClient;
import com.movie.finder.client.response.ClientMovie;
import com.movie.finder.dto.MovieDto;
import com.movie.finder.mapper.MovieMapper;
import com.movie.finder.model.Genre;
import com.movie.finder.model.Movie;
import com.movie.finder.model.MovieGenre;
import com.movie.finder.repo.GenreRepository;
import com.movie.finder.repo.MovieGenreRepository;
import com.movie.finder.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MovieScheduler {

    @Value("${pages.maximum}")
    public int maximumpages;

    @Autowired
    public MovieClient movieClient;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    MovieGenreRepository movieGenreRepository;

    @Scheduled(fixedRate = 600000)
    public void fetchMoviesFromApi(){

        int page=1;
        List<Genre> genresList = genreRepository.findAll();

        while (page<=maximumpages){
            List<ClientMovie> clientMovies = movieClient.getPage(page);
             populateClientDataToDB(clientMovies,page,genresList);
             page++;
        }

    }

    public void populateClientDataToDB(List<ClientMovie> clientMovies,int page,List<Genre>genres){
        Pageable pageable = PageRequest.of(page-1, 20);
        List<Movie> savedMovies;
        List<Movie>trackPopularity = new ArrayList<>();
        List<Movie> clientToModel = clientMovies.stream().map(movieMapper::ToEntity).toList();
        List<Movie> movieList = movieRepository.findAll(pageable).stream().toList();
        List<Movie> newMovies = new ArrayList<>();
        if(!movieList.isEmpty()) {
            for (Movie movie : movieList) {
                Optional<Movie> optional = clientToModel.stream().filter(model -> model.getTmdbId() == movie.getTmdbId()).findFirst();
                if(optional.isEmpty()){
                    newMovies.add(movie);
                }
            }
         savedMovies =   movieRepository.saveAll(newMovies);
        }else{
          savedMovies =  movieRepository.saveAll(clientToModel);
        }
        for(Movie movie : movieList){
            ClientMovie clientMovie = clientMovies.stream().filter(it -> it.getTmdbId()== movie.getTmdbId()).findFirst().orElse(null);
            if(clientMovie!=null){
                if(clientMovie.getPopularity() != movie.getPopularity()){
                    movie.setPopularity(clientMovie.getPopularity());
                    trackPopularity.add(movie);
                }
            }
            movieRepository.saveAll(trackPopularity);
        }


        List<MovieGenre> movieGenres = new ArrayList<>();
        for(Movie movie : savedMovies) {
            ClientMovie clientMovie = clientMovies.stream().filter(tmdbMovie -> tmdbMovie.getTmdbId() == movie.getTmdbId()).findFirst().orElse(null);
            if (clientMovie != null) {
                List<Integer> genreList = clientMovie.getGenres();
                for (Integer genre : genreList) {
                    movieGenres.add(new MovieGenre(new Movie(movie.getId()), new Genre(genre)));
                }
            }
        }
        movieGenreRepository.saveAll(movieGenres);
    }
}
