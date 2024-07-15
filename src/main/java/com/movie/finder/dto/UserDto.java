package com.movie.finder.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserDto {


    private String fullName;


    private String email;


    private Date createdAt;
}
