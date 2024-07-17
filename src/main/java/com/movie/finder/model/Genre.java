package com.movie.finder.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "genres")
@Getter
@Setter
public class Genre implements Serializable {

    public Genre(){}
    public Genre(int genreId) {
        this.genreId = genreId;
    }

    @Id
    private int genreId;

    private String genreName;

}
