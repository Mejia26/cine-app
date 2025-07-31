package com.cine.repo;

import com.cine.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    // Puedes agregar métodos personalizados si deseas, por ejemplo:
     List<Movie> findByGenre(String genre);
     List<Movie> findByTitleContainingIgnoreCase(String keyword);

}