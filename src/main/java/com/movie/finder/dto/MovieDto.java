package com.movie.finder.dto;

import com.movie.finder.model.Genre;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MovieDto implements Serializable {


    private int id;


    private String originalLanguage;


    private String originalTitle;


    private String releaseDate;


    private double popularity;


    private List<Genre> genres;
}
