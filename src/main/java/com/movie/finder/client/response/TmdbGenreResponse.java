package com.movie.finder.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movie.finder.model.Genre;
import lombok.Getter;

import java.util.List;

@Getter
public class TmdbGenreResponse {

    @JsonProperty("genres")
    public List<ClientGenre> genres;
}
