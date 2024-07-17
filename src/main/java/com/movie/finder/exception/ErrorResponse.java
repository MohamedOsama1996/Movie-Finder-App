package com.movie.finder.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    public ErrorCode errorCode;

    public int code;

    public String message;

    public ErrorResponse(int code, ErrorCode errorCode,String message) {
        this.code = code;
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
