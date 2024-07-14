package com.movie.finder.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientMovie {

    @JsonProperty("id")
    private int tmdbId;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("popularity")
    private float popularity;

    @JsonProperty("genre_ids")
    private List<Integer> genres;
}
