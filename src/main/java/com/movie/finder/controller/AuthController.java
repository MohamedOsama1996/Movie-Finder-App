package com.movie.finder.controller;

import com.movie.finder.documentation.DocumentationConstants;
import com.movie.finder.dto.UserDto;
import com.movie.finder.model.User;
import com.movie.finder.model.request.LoginUserRequest;
import com.movie.finder.model.request.RegisterUserRequest;
import com.movie.finder.model.response.LoginUserResponse;
import com.movie.finder.service.AuthService;
import com.movie.finder.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = DocumentationConstants.AuthControllerDescription.AUTH_API,
        description = DocumentationConstants.AuthControllerDescription.AUTH_DESCRIPTION)
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private  AuthService authenticationService;


    @Operation(summary = DocumentationConstants.AuthControllerDescription.REGISTER_USER_API,
            description = DocumentationConstants.AuthControllerDescription.REGISTER_USER_API_DESCRIPTION)
    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody RegisterUserRequest registerUserRequest) {
        UserDto registeredUser = authenticationService.signup(registerUserRequest);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = DocumentationConstants.AuthControllerDescription.LOGIN_USER_API,
            description = DocumentationConstants.AuthControllerDescription.LOGIN_USER_API_DESCRIPTION)
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> authenticate(@RequestBody LoginUserRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginUserResponse response = new LoginUserResponse();
        response.setToken(jwtToken);

        return ResponseEntity.ok(response);
    }
}