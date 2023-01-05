package com.example.ambsoftware;

import com.example.ambsoftware.dto.MovieDto;
import com.example.ambsoftware.dto.RatingDto;
import com.example.ambsoftware.service.MovieService;
import com.example.ambsoftware.service.RatingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MovieFactory implements CommandLineRunner {

    private final MovieService movieService;
    private final RatingService ratingService;

    public MovieFactory(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @Override
    public void run(String... args) throws Exception {
        createMovies();
        createRatings();
    }

    private void createMovies() {
        List<MovieDto> movies = new ArrayList<>(Arrays.asList(
                new MovieDto(1962, "horror", "asdfg", new ArrayList<>()),
                new MovieDto(1999, "drama", "qwerty", new ArrayList<>()),
                new MovieDto(2007, "comedy", "tyuiop", new ArrayList<>()),
                new MovieDto(2016, "sci-fi", "ccvbnm", new ArrayList<>())
        ));
        for (MovieDto m : movies) {
            movieService.createMovie(m);
        }
    }

    private void createRatings() {
        List<RatingDto> ratings = new ArrayList<>(Arrays.asList(
                new RatingDto(1, null),
                new RatingDto(2, null),
                new RatingDto(3, null),
                new RatingDto(4, null),
                new RatingDto(5, null)
        ));
        for (RatingDto r : ratings) {
            ratingService.createRate(r);
        }
    }
}
