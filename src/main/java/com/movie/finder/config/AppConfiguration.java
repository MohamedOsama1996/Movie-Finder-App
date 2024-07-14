package com.movie.finder.config;

import com.movie.finder.dto.MovieDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class AppConfiguration {



    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public RedisTemplate<String, List<MovieDto>> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<MovieDto>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
