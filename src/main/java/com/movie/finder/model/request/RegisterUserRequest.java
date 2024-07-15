package com.movie.finder.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {

    private String email;

    private String password;

    private String fullName;
}
