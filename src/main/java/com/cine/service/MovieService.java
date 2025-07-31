package com.cine.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.cine.model.Movie;

public interface MovieService {

    List<Movie> getAllMovies();

    Optional<Movie> getMovieById(UUID id);

    Movie createMovie(Movie movie);

    Movie updateMovie(UUID id, Movie updatedMovie);

    void deleteMovie(UUID id);
    
    boolean movieExists(UUID id);
}