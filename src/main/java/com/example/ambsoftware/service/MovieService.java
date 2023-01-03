package com.example.ambsoftware.service;

import com.example.ambsoftware.domain.Movie;
import com.example.ambsoftware.domain.Rating;
import com.example.ambsoftware.dto.MovieDto;
import com.example.ambsoftware.exceptions.MovieTaskException;
import com.example.ambsoftware.repository.MovieRepository;
import com.example.ambsoftware.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    public MovieService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<Movie> getAll() {
        List<Movie> l = new ArrayList<>();
        Iterable<Movie> all = movieRepository.findAll();
        for (Movie m : all) {
            l.add(m);
        }
        return l;
    }

    public Movie createMovie(MovieDto movieDto) {
        Movie movie = Movie.builder()
                .releaseYear(movieDto.getReleaseYear())
                .category(movieDto.getCategory())
                .description(movieDto.getDescription())
                .ratings(movieDto.getRatings())
                .build();
        movieRepository.save(movie);
        return movie;
    }

    public Movie updateMovie(Long id, MovieDto movieDto) {
        Movie movieToUpdate = getMovieById(id);
        movieRepository.save(Movie.builder()
                .id(movieToUpdate.getId())
                .releaseYear(movieDto.getReleaseYear())
                .category(movieDto.getCategory())
                .description(movieDto.getDescription())
                .ratings(movieDto.getRatings())
                .build());
        return movieToUpdate;
    }

    public void deleteMovieById(Long id) {
        movieRepository.delete(getMovieById(id));
    }

    public List<Movie> findBySequence(String sequence) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : getAll()) {
            if (m.getDescription().contains(sequence) || m.getCategory().equals(sequence)) {
                result.add(m);
            }
        }
        return result;
    }

    public void rateMovie(Long movieId, Rating rating) {
        Movie movie = getMovieById(movieId);
        if (!rating.equals(null) && rating.getMovie().equals(null)) {
            Optional<Rating> rate = ratingRepository.findById(rating.getId());
            rate.get().setMovie(movie);
            movie.getRatings().add(rating);
        } else if (!rating.getMovie().equals(null)) {
            throw new MovieTaskException("The rating just have movie");
        } else {
            throw new MovieTaskException("The rating can't be null");
        }
    }

    public List<Movie> findMoviesByCategory(String category) {
        List<Movie> result = new ArrayList<>();
        if (validateCategory(category)) {
            for (Movie movie : movieRepository.findAll()) {
                if (movie.getCategory().equals(category)) {
                    result.add(movie);
                }
            }
        }
        return result;
    }

    private boolean validateCategory(String category) {
        return !category.isEmpty();
    }

    private Movie getMovieById(Long id) {
        Optional<Movie> movieById = Optional.of(movieRepository.findById(id)
                .orElseThrow(() -> new MovieTaskException("Couldn't find specific movie")));
        return movieById.get();
    }


}
