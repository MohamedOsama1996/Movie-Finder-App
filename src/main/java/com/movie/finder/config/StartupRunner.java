package com.movie.finder.config;

import com.movie.finder.client.GenreClient;
import com.movie.finder.client.response.ClientGenre;
import com.movie.finder.mapper.GenreMapper;
import com.movie.finder.model.Genre;
import com.movie.finder.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    GenreClient genreClient;

    @Autowired
    GenreRepository genreRepository;

    @Override
    public void run(String... args) throws Exception {

        List<ClientGenre> clientGenres = genreClient.getGenres();
        List<Genre> genres = clientGenres.stream().map(GenreMapper::toEntity).toList();
        genreRepository.saveAll(genres);

    }
}
