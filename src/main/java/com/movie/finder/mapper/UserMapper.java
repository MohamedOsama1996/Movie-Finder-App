package com.movie.finder.mapper;

import com.movie.finder.dto.UserDto;
import com.movie.finder.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user){

        UserDto userDto = new UserDto();
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
}
