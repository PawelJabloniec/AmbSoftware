package com.example.ambsoftware.repository;

import com.example.ambsoftware.domain.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    public Movie findByCategory(String category);
}
