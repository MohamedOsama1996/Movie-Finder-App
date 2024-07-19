package com.movie.finder.config;


import com.movie.finder.scheduler.GenreScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class StartupRunner implements CommandLineRunner {

   @Autowired
    GenreScheduler genreScheduler;

    @Override
    public void run(String... args) throws Exception {

        genreScheduler.getGenresFromApi();

    }
}
