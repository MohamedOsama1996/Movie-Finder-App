package com.movie.finder.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TmdbMovieResponse {

    @JsonProperty("results")
    private List<ClientMovie> clientMovieList;
}
