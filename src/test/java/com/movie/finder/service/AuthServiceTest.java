package com.movie.finder.service;

import com.movie.finder.dto.UserDto;
import com.movie.finder.exception.MovieFinderException;
import com.movie.finder.mapper.UserMapper;
import com.movie.finder.model.User;
import com.movie.finder.model.request.LoginUserRequest;
import com.movie.finder.model.request.RegisterUserRequest;
import com.movie.finder.repo.UserRepository;
import com.movie.finder.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    AuthServiceImpl authService;

    private User user;

    private RegisterUserRequest registerUserRequest;

    private LoginUserRequest loginUserRequest;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        user = new User(Long.valueOf("1"));
        user.setFullName("Mohamed Osama");
        user.setEmail("test@test.com");

        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFullName("Mohamed Osama");
        registerUserRequest.setEmail("test@test.com");
        registerUserRequest.setPassword("123456");
    }

    @Test
    public void testSignUp_Success(){
        UserDto userDto = new UserDto();
        userDto.setFullName("Mohamed Osama");
        userDto.setEmail("test@test.com");
        when(userRepository.findByEmail(registerUserRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerUserRequest.getPassword())).thenReturn("hashed password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);
        userDto = authService.signup(registerUserRequest);
        assertEquals(userDto.getFullName(),registerUserRequest.getFullName());
        assertEquals(userDto.getEmail(),registerUserRequest.getEmail());
    }

    @Test
    public void testSignup_EmailAlreadyExists() throws Exception {

        String email = "test@test.com";
        user = new User(Long.valueOf("1"));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        assertThrows(MovieFinderException.class,() -> authService.signup(registerUserRequest));
    }

    @Test
    public void testSignup_UnexpectedError() throws Exception {
        when(passwordEncoder.encode(anyString())).thenThrow(IllegalArgumentException.class);
        assertThrows(MovieFinderException.class,() -> authService.signup(registerUserRequest));
    }

    @Test
    public void testLogin_Success(){
        loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail("test@test.com");
        loginUserRequest.setPassword("123456");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(loginUserRequest.getEmail())).thenReturn(Optional.of(user));

        User actualUser = authService.authenticate(loginUserRequest);
        assertEquals(user, actualUser);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(loginUserRequest.getEmail());

    }

    @Test
    public void testAuthenticateUser_NotFound() {
        // Given
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail("test@test.com");
        loginUserRequest.setPassword("123456");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(loginUserRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(MovieFinderException.class, () -> authService.authenticate(loginUserRequest));

    }
}
