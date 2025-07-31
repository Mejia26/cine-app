package com.cine.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.model.Movie;
import com.cine.repo.MovieRepository;
import com.cine.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	private static Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
	
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
    	logger.info("retrieving list of movies");
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getMovieById(UUID id) {
    	logger.info("retrieving movie by id: " + id);
        return movieRepository.findById(id);
    }

    @Override
    public Movie createMovie(Movie movie) {
    	logger.info("creating a movie");
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(UUID id, Movie updatedMovie) {
    	logger.info("updating a movie by id: " + id);
        return movieRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedMovie.getTitle());
                    existing.setDirector(updatedMovie.getDirector());
                    existing.setGenre(updatedMovie.getGenre());
                    existing.setReleaseDate(updatedMovie.getReleaseDate());
                    existing.setRating(updatedMovie.getRating());
                    return movieRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    @Override
    public void deleteMovie(UUID id) {
    	logger.info("deleting a movie by id: "+ id);
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }
}