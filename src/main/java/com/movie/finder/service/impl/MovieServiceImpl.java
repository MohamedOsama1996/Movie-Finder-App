package com.movie.finder.service.impl;

import com.movie.finder.client.MovieClient;
import com.movie.finder.client.response.ClientMovie;
import com.movie.finder.dto.MovieDto;
import com.movie.finder.mapper.MovieMapper;
import com.movie.finder.model.Genre;
import com.movie.finder.model.Movie;
import com.movie.finder.repo.GenreRepository;
import com.movie.finder.repo.MovieRepository;
import com.movie.finder.service.MovieService;
import com.movie.finder.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
   public MovieRepository movieRepository;

    @Autowired
   public  GenreRepository genreRepository;

    @Autowired
   public MovieMapper movieMapper;

   @Autowired
   public RedisTemplate<String,List<MovieDto>> redisTemplate;

    @Override
    public List<MovieDto> getMoviesByPage(int page) {

        String cacheKey = Utility.CACHE_MOVIE_KEY.concat(String.valueOf(page));

        List<MovieDto> cachedMovies = redisTemplate.opsForValue().get(cacheKey);
        if(cachedMovies !=null){
            return  cachedMovies;
        }else{
            List<MovieDto> movieDtos = new ArrayList<>();
            Sort.Direction direction = Sort.Direction.fromString("desc");
            Pageable pageable = PageRequest.of(page-1, 20,Sort.by(direction,"popularity"));
            Page<Movie> movieList = movieRepository.findAll(pageable);
            if(!movieList.isEmpty()){
                 movieDtos = movieList.stream().map(movie -> movieMapper.EntityToDto(movie)).toList();
                 redisTemplate.opsForValue().set(cacheKey,movieDtos,1, TimeUnit.HOURS);
                return movieDtos;
            }else{
                return movieDtos;
            }
        }
    }
}
