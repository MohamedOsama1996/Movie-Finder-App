package com.movie.finder.documentation;

public interface DocumentationConstants {

    interface AuthControllerDescription{

        String AUTH_API="Auth Controller";
        String AUTH_DESCRIPTION = "This Controller is responsible for user registration and authentication";

        String REGISTER_USER_API = "This Api to register a user";
        String REGISTER_USER_API_DESCRIPTION = "Post Api to add new users to the system ";

        String LOGIN_USER_API = "This Api To Authenticate a user";
        String LOGIN_USER_API_DESCRIPTION = "This Api To Authenticate a registered user to login";

    }
    interface MovieControllerDescription{

        String MOVIE_API="Movie Controller";
        String MOVIE_DESCRIPTION = "This Controller is responsible for listing movies,rating movies , adding to favorite";

        String GET_MOVIES_API = "Get Movies Api";
        String GET_MOVIES_API_DESCRIPTION ="An Api to list movies in our system";

        String POST_ADD_MOVIE_API ="Post Movie To Favorite";
        String POST_ADD_MOVIE_API_DESCRIPTION ="An Api To add movies to Users Favorite";

        String POST_RATE_MOVIE_API = "Post Request to Rate Movie";
        String POST_RATE_MOVIE_API_DESCRIPTION = "Make User Rate Movies";


        String GET_SEARCH_MOVIE_API = "Get Request to Search For a Movie";
        String GET_SEARCH_MOVIE_API_DESCRIPTION = "Get Request to Search For a Movie based on genre ";

    }
}
