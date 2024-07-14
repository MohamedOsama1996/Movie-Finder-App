CREATE TABLE movies (
  id INT PRIMARY KEY AUTO_INCREMENT,
  tmdb_id INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  lang VARCHAR(3) NOT NULL,
  release_date VARCHAR(20),
  rating_count INT DEFAULT 0,
  average_rating DECIMAL(2, 1) ,
  popularity DECIMAL(7,3)-- Stores rating up to two decimal places
);

CREATE TABLE genres(
  genre_id INT PRIMARY KEY NOT NULL,
  genre_name VARCHAR(50) NOT NULL
);

CREATE TABLE movies_genres(

  movie_genre_id INT PRIMARY KEY AUTO_INCREMENT,
  movie_id INT NOT NULL,
  genre_id  INT NOT NULL,
  FOREIGN KEY (movie_id) REFERENCES movies(id),
  FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

