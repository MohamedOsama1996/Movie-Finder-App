package com.movie.finder.scheduler;


import com.movie.finder.client.MovieClient;
import com.movie.finder.client.response.ClientMovie;
import com.movie.finder.dto.MovieDto;
import com.movie.finder.exception.ErrorCode;
import com.movie.finder.exception.MovieFinderException;
import com.movie.finder.mapper.MovieMapper;
import com.movie.finder.model.Genre;
import com.movie.finder.model.Movie;
import com.movie.finder.model.MovieGenre;
import com.movie.finder.repo.GenreRepository;
import com.movie.finder.repo.MovieGenreRepository;
import com.movie.finder.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Profile("!test")
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
        while (page<=maximumpages){
            List<ClientMovie> clientMovies = movieClient.getPage(page);
             populateClientDataToDB(clientMovies,page);
             page++;
        }

    }

    public void populateClientDataToDB(List<ClientMovie> clientMovies,int page) {
        Pageable pageable = PageRequest.of(page - 1, 20);
        List<Movie> savedMovies;
        List<Movie> trackPopularity = new ArrayList<>();
        List<Movie> deleteUnPopular = new ArrayList<>();
        try {
            List<Movie> clientToModel = clientMovies.stream().map(movieMapper::ToEntity).toList();
            List<Movie> movieList = movieRepository.findAll(pageable).stream().toList();
            List<Movie> newMovies = new ArrayList<>();

            // this part to track if suddenly a movie became unpopular so it removed from certain page
            // then it should be removed from the db to track live changes
            for (Movie movie : movieList) {
                ClientMovie clientMovie = clientMovies.stream().filter(it -> it.getTmdbId() == movie.getTmdbId()).findFirst().orElse(null);
                if (clientMovie == null) {
                    deleteUnPopular.add(movie);
                }
                movieRepository.deleteAll(deleteUnPopular);
            }

            //this part is to change popularity of a movie
            // if it is changed in the tmdb api and it is still in the current page
            for (Movie movie : movieList) {
                ClientMovie clientMovie = clientMovies.stream().filter(it -> it.getTmdbId() == movie.getTmdbId()).findFirst().orElse(null);
                if (clientMovie != null) {
                    if (clientMovie.getPopularity() != movie.getPopularity()) {
                        movie.setPopularity(clientMovie.getPopularity());
                        trackPopularity.add(movie);
                    }
                }
                movieRepository.saveAll(trackPopularity);
            }


            for (Movie movie : movieList) {
                ClientMovie clientMovie = clientMovies.stream().filter(it -> it.getTmdbId() == movie.getTmdbId()).findFirst().orElse(null);
                if (clientMovie != null) {
                    if (clientMovie.getPopularity() != movie.getPopularity()) {
                        movie.setPopularity(clientMovie.getPopularity());
                        trackPopularity.add(movie);
                    }
                }
                movieRepository.saveAll(trackPopularity);
            }
            if (!movieList.isEmpty()) {
                for (Movie movie : movieList) {
                    Optional<Movie> optional = clientToModel.stream().filter(model -> model.getTmdbId() == movie.getTmdbId()).findFirst();
                    if (optional.isEmpty()) {
                        newMovies.add(movie);
                    }
                }
                savedMovies = movieRepository.saveAll(newMovies);
            } else {
                savedMovies = movieRepository.saveAll(clientToModel);
            }


            List<MovieGenre> movieGenres = new ArrayList<>();
            for (Movie movie : savedMovies) {
                ClientMovie clientMovie = clientMovies.stream().filter(tmdbMovie -> tmdbMovie.getTmdbId() == movie.getTmdbId()).findFirst().orElse(null);
                if (clientMovie != null) {
                    List<Integer> genreList = clientMovie.getGenres();
                    for (Integer genre : genreList) {
                        movieGenres.add(new MovieGenre(new Movie(movie.getId()), new Genre(genre)));
                    }
                }
            }
            movieGenreRepository.saveAll(movieGenres);
        }catch (MovieFinderException ex){
            throw new MovieFinderException(ErrorCode.MF_CLIENT_GENRE_500, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
