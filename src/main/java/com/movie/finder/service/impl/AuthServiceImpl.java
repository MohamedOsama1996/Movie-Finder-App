package com.movie.finder.service.impl;

import com.movie.finder.dto.UserDto;
import com.movie.finder.exception.ErrorCode;
import com.movie.finder.exception.MovieFinderException;
import com.movie.finder.mapper.UserMapper;
import com.movie.finder.model.User;
import com.movie.finder.model.request.LoginUserRequest;
import com.movie.finder.model.request.RegisterUserRequest;
import com.movie.finder.repo.UserRepository;
import com.movie.finder.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDto signup(RegisterUserRequest registerUserRequest) {
        Optional<User> userOptional = userRepository.findByEmail(registerUserRequest.getEmail());
        if(userOptional.isPresent()){
            throw new MovieFinderException(ErrorCode.MF_USR_ERR_409, HttpStatus.CONFLICT);
        }
        try {
            User user = new User();
            user.setFullName(registerUserRequest.getFullName());
            user.setEmail(registerUserRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
            user.setCreatedAt(Date.valueOf(LocalDate.now()));
            return userMapper.toDto(userRepository.save(user));
        }catch (MovieFinderException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new MovieFinderException(ErrorCode.MF_ERR_500, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User authenticate(LoginUserRequest loginUserRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserRequest.getEmail(),
                        loginUserRequest.getPassword()
                )
        );

        return userRepository.findByEmail(loginUserRequest.getEmail())
                .orElseThrow();
    }
}
