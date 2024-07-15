package com.movie.finder.controller;

import com.movie.finder.dto.UserDto;
import com.movie.finder.model.User;
import com.movie.finder.model.request.LoginUserRequest;
import com.movie.finder.model.request.RegisterUserRequest;
import com.movie.finder.model.response.LoginUserResponse;
import com.movie.finder.service.AuthService;
import com.movie.finder.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private  AuthService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody RegisterUserRequest registerUserRequest) {
        UserDto registeredUser = authenticationService.signup(registerUserRequest);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> authenticate(@RequestBody LoginUserRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginUserResponse response = new LoginUserResponse();
        response.setToken(jwtToken);

        return ResponseEntity.ok(response);
    }
}