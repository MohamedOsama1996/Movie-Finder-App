package com.movie.finder.exception;

import org.springframework.http.HttpStatus;

public class MovieFinderException extends RuntimeException{


    public ErrorCode errorCode;
    public HttpStatus httpStatus;

    public MovieFinderException(ErrorCode errorCode,HttpStatus httpStatus){
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
