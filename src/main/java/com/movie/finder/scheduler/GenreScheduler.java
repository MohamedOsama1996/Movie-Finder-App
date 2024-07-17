package com.movie.finder.scheduler;

import com.movie.finder.client.GenreClient;
import com.movie.finder.client.response.ClientGenre;
import com.movie.finder.mapper.GenreMapper;
import com.movie.finder.model.Genre;
import com.movie.finder.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenreScheduler {

    @Autowired
    GenreClient genreClient;

    @Autowired
    GenreRepository genreRepository;

    @Scheduled(fixedRate = 300000)
    public void getGenresFromApi() {
        List<ClientGenre> clientGenres = genreClient.getGenres();
        List<Genre> genres = clientGenres.stream().map(GenreMapper::toEntity).toList();
        genreRepository.saveAll(genres);
    }
}
