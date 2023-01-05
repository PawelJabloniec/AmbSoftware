package com.example.ambsoftware.controller;

import com.example.ambsoftware.domain.Rating;
import com.example.ambsoftware.dto.RatingDto;
import com.example.ambsoftware.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsController {
    private final RatingService ratingsService;

    public RatingsController(RatingService ratingsService) {
        this.ratingsService = ratingsService;
    }

    @PostMapping("/create")
    public final ResponseEntity<Rating> createRating(@RequestBody @Valid RatingDto ratingDto) {
        Rating rating = ratingsService.createRate(ratingDto);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Rating> getAll() {
        return ratingsService.getAll();
    }
}
