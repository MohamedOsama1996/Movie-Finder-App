package com.movie.finder.service;

import com.movie.finder.dto.UserDto;
import com.movie.finder.model.User;
import com.movie.finder.model.request.LoginUserRequest;
import com.movie.finder.model.request.RegisterUserRequest;

public interface AuthService {

     UserDto signup(RegisterUserRequest registerUserRequest);


     User authenticate(LoginUserRequest loginUserRequest);
}
