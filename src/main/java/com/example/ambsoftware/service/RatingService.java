package com.example.ambsoftware.service;

import com.example.ambsoftware.domain.Rating;
import com.example.ambsoftware.dto.RatingDto;
import com.example.ambsoftware.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getAll() {
        List<Rating> l = new ArrayList<>();
        Iterable<Rating> all = ratingRepository.findAll();
        for (Rating r : all) {
            l.add(r);
        }
        return l;
    }

    public Rating createRate(RatingDto ratingDto) {
        return ratingRepository.save(Rating.builder()
                .rate(ratingDto.getRate())
                .movie(ratingDto.getMovie())
                .build());
    }
}
