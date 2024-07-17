package com.movie.finder.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieFinderException.class)
    public ResponseEntity<ErrorResponse> handleMovieFinderException(MovieFinderException ex){
        ErrorResponse errorResponse= new ErrorResponse(ex.httpStatus.value(),ex.errorCode,ex.errorCode.getMessage());
        return ResponseEntity.status(errorResponse.code).body(errorResponse);
    }
}
