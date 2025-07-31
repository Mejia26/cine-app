package com.cine.service.serviceImpl;

import java.util.Comparator;
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
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }
    
    @Override
    public boolean movieExists(UUID id) {
        return movieRepository.existsById(id);
    }
    
    
    @Override
    public List<Movie> getMovies(String genre, String director, String sort) {
    	logger.info("retrieving list of movies");

        List<Movie> all = movieRepository.findAll();

        if (genre != null && !genre.isBlank()) {
            all = all.stream()
                    .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                    .toList();
        }

        if (director != null && !director.isBlank()) {
            all = all.stream()
                    .filter(m -> m.getDirector().equalsIgnoreCase(director))
                    .toList();
        }

        if ("asc".equalsIgnoreCase(sort)) {
            all = all.stream()
                    .sorted(Comparator.comparing(Movie::getReleaseDate))
                    .toList();
        } else if ("desc".equalsIgnoreCase(sort)) {
            all = all.stream()
                    .sorted(Comparator.comparing(Movie::getReleaseDate).reversed())
                    .toList();
        }

        return all;
    }
}