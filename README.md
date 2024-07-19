#Weloce to My Movie Finder App

This project is a Spring Boot application that provides a movie search functionality. It integrates with external APIs to fetch movie data and stores user preferences.

## Prerequisites

- Java 17 or higher
- Maven
- Docker and Docker Compose (for running with containers)

## Running the Application

### Using Docker

1. Clone the repository: git clone https://github.com/MohamedOsama1996/Movie-Finder-App.git
2. Navigate to the project directory: cd Movie-Finder-App
3. Build the project: mvn clean package
4. docker-compose up
After this steps the project should run smoothly and you can communicate with the project through port 8080

## API DOCUMENTATION 
You can check the api documentation through swagger on the following link : http://localhost:8080/api/v1/swagger-ui/index.html

## Describing How Data Is Added To The DB And How To Add More Data :
When the server starts up you will find in the configuration package : StartupRunner file, this file is responsible for getting the genres from the tmdb api then there is a scheduler function that gets the movies from
tmdb api based on an attribute named pages.maximum , if you want to add more data you should increase the number of pages that you can get from the api.

## Describing The Caching Mechanism : 
When data is added to db by scheduler job , when a user need the data from db we get only from db for the first time then it is cached in redis for one hour then it expires.
The key is tmdb:movies:page:(number of page).
Thanks.
