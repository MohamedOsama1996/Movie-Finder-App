package com.movie.finder.client;


import com.movie.finder.client.response.ClientGenre;

import java.util.List;

public interface GenreClient {

    List<ClientGenre> getGenres();
}
