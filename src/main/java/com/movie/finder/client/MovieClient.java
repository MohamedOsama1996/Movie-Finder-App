package com.movie.finder.client;

import com.movie.finder.client.response.ClientMovie;

import java.util.List;

public interface MovieClient {

     List<ClientMovie> getPage(int page);
}
