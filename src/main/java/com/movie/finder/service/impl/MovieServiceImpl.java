package com.movie.finder.service.impl;

import com.movie.finder.dto.MovieDto;
import com.movie.finder.exception.ErrorCode;
import com.movie.finder.exception.MovieFinderException;
import com.movie.finder.mapper.MovieMapper;
import com.movie.finder.model.*;
import com.movie.finder.repo.*;
import com.movie.finder.service.MovieService;
import com.movie.finder.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
   public MovieRepository movieRepository;

    @Autowired
   public UserRepository userRepository;

    @Autowired
    public UserMovieRatingRepository userMovieRatingRepository;

    @Autowired
   public MovieMapper movieMapper;

   @Autowired
   public RedisTemplate<String,List<MovieDto>> redisTemplate;

   @Autowired
   public UserMovieRepository userMovieRepository;

   @Autowired
   public GenreRepository genreRepository;

   @Autowired
   public MovieGenreRepository movieGenreRepository;

    @Override
    public List<MovieDto> getMoviesByPage(int page) {

        String cacheKey = Utility.CACHE_MOVIE_KEY.concat(String.valueOf(page));

        try {
            List<MovieDto> cachedMovies = redisTemplate.opsForValue().get(cacheKey);
            if (cachedMovies != null) {
                return cachedMovies;
            } else {
                List<MovieDto> movieDtos = new ArrayList<>();
                Sort.Direction direction = Sort.Direction.fromString("desc");
                Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, "popularity"));
                Page<Movie> movieList = movieRepository.findAll(pageable);
                if (!movieList.isEmpty()) {
                    movieDtos = movieList.stream().map(movie -> movieMapper.EntityToDto(movie)).toList();
                    redisTemplate.opsForValue().set(cacheKey, movieDtos, 1, TimeUnit.HOURS);
                    return movieDtos;
                } else {
                    return movieDtos;
                }
            }
        }catch (Exception ex){
            throw new MovieFinderException(ErrorCode.MF_ERR_500, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void AddMovieToFavorites(int movieId, User user) {


            Optional<Movie> movie = movieRepository.findById(movieId);
            if(movie.isEmpty()) throw new MovieFinderException(ErrorCode.MF_MOVIE_ERR_404,HttpStatus.NOT_FOUND);
            try {
                Long userId = userRepository.findByEmail(user.getEmail()).get().getId();
                UserMovie userMovie = new UserMovie();
                userMovie.setMovie(new Movie(movie.get().getId()));
                userMovie.setUser(new User(userId));
                userMovieRepository.save(userMovie);
            }catch (MovieFinderException ex){
                throw ex;
            }catch (DataAccessException ex){
                throw new MovieFinderException(ErrorCode.MF_ERR_500, HttpStatus.INTERNAL_SERVER_ERROR);

            }


    }

    @Override
    public List<MovieDto> getUserFavorites(User user) {
         List<UserMovie> userMovies = userMovieRepository.findByUser(new User(user.getId()));
         List<Movie> movieFavorites = userMovies.stream().map(UserMovie::getMovie).toList();
         return movieFavorites.stream().map(movie -> movieMapper.EntityToDto(movie)).toList();
    }

    @Override
    public void userRateMovie(int movieId,double userRating, User user) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()) throw new MovieFinderException(ErrorCode.MF_MOVIE_ERR_404,HttpStatus.NOT_FOUND);
        Movie movie = movieOptional.get();
        Long userId = userRepository.findByEmail(user.getEmail()).get().getId();
        Optional<UserMovieRating> userMovieRatingOptional = userMovieRatingRepository.findByUserAndMovie(new User(userId),new Movie(movieId));
        if(userMovieRatingOptional.isPresent()) throw  new MovieFinderException(ErrorCode.MF_MOVIE_ERR_409,HttpStatus.CONFLICT);
        try {
            UserMovieRating userMovieRating = new UserMovieRating();
            userMovieRating.setMovie(new Movie(movie.getId()));
            userMovieRating.setUser(new User(userId));
            userMovieRating.setUserMovieRating(userRating);
            userMovieRatingRepository.save(userMovieRating);
            movie.setRatingCount(movie.getRatingCount()+1);
            movie.setTotalRating(movie.getTotalRating()+userRating);
            movie.setAverageRating(movie.getTotalRating()/movie.getRatingCount());
            movieRepository.save(movie);
        }catch (DataAccessException ex){
            throw new MovieFinderException(ErrorCode.MF_ERR_500, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<MovieDto> getMoviesByGenre(String genre,int page) {

        Optional<Genre> genreOptional = genreRepository.findByGenreName(genre);
        if(genreOptional.isEmpty()) throw new MovieFinderException(ErrorCode.MF_GENRE_ERR_404,HttpStatus.NOT_FOUND);
        try{
            Pageable pageable = PageRequest.of(page, 20);

            List<MovieGenre> movieGenres = movieGenreRepository.findByGenre_GenreId(genreOptional.get().getGenreId(),pageable);
            return movieGenres.stream().map(movieGenre -> movieMapper.EntityToDto(movieGenre.getMovie())).toList();

        }catch (DataAccessException ex){
            throw new MovieFinderException(ErrorCode.MF_ERR_500, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
