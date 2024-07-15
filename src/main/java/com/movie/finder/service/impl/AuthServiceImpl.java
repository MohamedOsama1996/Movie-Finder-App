package com.movie.finder.service.impl;

import com.movie.finder.dto.UserDto;
import com.movie.finder.mapper.UserMapper;
import com.movie.finder.model.User;
import com.movie.finder.model.request.LoginUserRequest;
import com.movie.finder.model.request.RegisterUserRequest;
import com.movie.finder.repo.UserRepository;
import com.movie.finder.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

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
        User user = new User();
        user.setFullName(registerUserRequest.getFullName());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setCreatedAt(Date.valueOf(LocalDate.now()));
        return userMapper.toDto(userRepository.save(user));
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
