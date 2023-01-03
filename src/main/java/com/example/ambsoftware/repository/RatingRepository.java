package com.example.ambsoftware.repository;

import com.example.ambsoftware.domain.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
}
