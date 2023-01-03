package com.example.ambsoftware.service;

import com.example.ambsoftware.domain.Rating;
import com.example.ambsoftware.dto.RatingDto;
import com.example.ambsoftware.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating createRate(RatingDto ratingDto) {
        Rating rating = Rating.builder()
                .rate(ratingDto.getRate())
                .movie(ratingDto.getMovie())
                .build();
        ratingRepository.save(rating);
        return rating;
    }
}
