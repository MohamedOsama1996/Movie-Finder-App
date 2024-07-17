package com.movie.finder.exception;

public enum ErrorCode {

    MF_ERR_401("MF_ERR_401","Please Check your Token"),
    MF_TOKEN_ERR_401("MF_ERR_401"," Check Your Authorization Header"),
    MF_USR_ERR_409("MF_USR_ERR_409","User Already Exists"),
    MF_ERR_500("MF_USR_ERR_409","Something Went Wrong"),
    MF_MOVIE_ERR_404("MF_MOVIE_ERR_404","Movie is not found"),
    MF_MOVIE_ERR_409("MF_MOVIE_ERR_409","you cant rate a movie you rated it before"),
    MF_CLIENT_GENRE_500("MF_CLIENT_GENRE_500","Cant Reach Tmdb Api Right now"),
    MF_TOKEN_500("MF_TOKEN_500","Something went wrong while building token"),
    MF_TOKEN_501("MF_TOKEN_500","Something Went Wrong While Extracting Claims"),
    MF_GENRE_ERR_404("MF_GENRE_ERR_404","Genre Not Found");


    private final String error;
    private final String message;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    // Add constructor with arguments
    ErrorCode(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
