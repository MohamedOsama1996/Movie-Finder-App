package com.movie.finder.client.impl;

import com.movie.finder.client.MovieClient;
import com.movie.finder.client.response.TmdbMovieResponse;
import com.movie.finder.client.response.ClientMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieClientImpl implements MovieClient {

    @Autowired
    public RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    public String TMDB_API_KEY;

    @Value("${tmdb.api.url}")
    public String TMDB_API_BASE_URL;

    @Override
    public List<ClientMovie> getPage(int page) {

        String url = String.format("%s/discover/movie?api_key=%s&page=%d",TMDB_API_BASE_URL, TMDB_API_KEY,page);
        ResponseEntity<TmdbMovieResponse>  tmdbResponse=restTemplate.exchange(url, HttpMethod.GET,null, TmdbMovieResponse.class);
        return  tmdbResponse.getBody().getClientMovieList();
    }
}
