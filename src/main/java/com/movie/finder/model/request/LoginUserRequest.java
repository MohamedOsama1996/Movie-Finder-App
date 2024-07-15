package com.movie.finder.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequest {

    private String email;
    private String password;
}
