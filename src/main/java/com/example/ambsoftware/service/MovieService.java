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
        return movieRepository.save(Movie.builder()
                .releaseYear(movieDto.getReleaseYear())
                .category(movieDto.getCategory())
                .description(movieDto.getDescription())
                .ratings(movieDto.getRatings())
                .build());
    }

    public Movie updateMovie(Long id, MovieDto movieDto) {
        Movie movieToUpdate = getMovieById(id);
        return movieRepository.save(Movie.builder()
                .id(movieToUpdate.getId())
                .releaseYear(movieDto.getReleaseYear())
                .category(movieDto.getCategory())
                .description(movieDto.getDescription())
                .ratings(movieDto.getRatings())
                .build());
    }

    public void deleteMovieById(Long id) {
        movieRepository.delete(getMovieById(id));
    }

    public List<Movie> findBySequence(String sequence) {
        return iterateThroughListOfMovies(sequence);
    }

    private List<Movie> iterateThroughListOfMovies(String sequence) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : getAll()) {
            if (checkIfDataContainsSequence(sequence, m)) {
                result.add(m);
            }
        }
        return result;
    }

    private boolean checkIfDataContainsSequence(String sequence, Movie m) {
        return m.getDescription().contains(sequence) || m.getCategory().equals(sequence);
    }

    public void rateMovie(Long movieId, Long ratingId) {
        Movie movie = getMovieById(movieId);
        Rating rate = getRating(ratingId).get();
        if (rate != null && rate.getMovie() == null) {
            rate.setMovie(movie);
            movie.getRatings().add(rate);
        } else if (rate.getMovie() != null) {
            throw new MovieTaskException("The rating just have movie");
        } else {
            throw new MovieTaskException("The rating can't be null");
        }
    }

    private Optional<Rating> getRating(Long ratingId) {
        return Optional.of(ratingRepository.findById(ratingId)
                .orElseThrow(() -> new MovieTaskException("Couldn't find specific movie")));
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
