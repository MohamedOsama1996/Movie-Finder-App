package com.movie.finder.mapper;

import com.movie.finder.client.response.ClientGenre;
import com.movie.finder.model.Genre;

public class GenreMapper {



    public static Genre toEntity(ClientGenre clientGenre){
        Genre genre = new Genre(clientGenre.getId());
        genre.setGenreName(clientGenre.getName());
        return genre;
    }
}
