package com.example.ambsoftware.controller;

import com.example.ambsoftware.domain.Movie;
import com.example.ambsoftware.dto.MovieDto;
import com.example.ambsoftware.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping("/authorities")
    public Map<String, Object> getPrincipalInfo(JwtAuthenticationToken principal) {

        Collection<String> authorities = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Map<String, Object> info = new HashMap<>();
        info.put("name", principal.getName());
        info.put("authorities", authorities);
        info.put("tokenAttributes", principal.getTokenAttributes());

        return info;
    }


    @PostMapping("/create")
    public final ResponseEntity<Movie> createMovie(@RequestBody @Valid MovieDto movieDto) {
        Movie movie = movieService.createMovie(movieDto);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public final ResponseEntity<Movie> updateMovie(@RequestBody @Valid MovieDto movieDto, @PathVariable Long id) {
        Movie updatedMovie = movieService.updateMovie(id, movieDto);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public final ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok("Succesfully deleted the movie!");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Movie> findBySequence(@PathVariable String seq) {
        return movieService.findBySequence(seq);
    }

    @PostMapping("/rateMovie")
    public final void rateMovie(@RequestParam(name = "ratingId") Long ratingId, @RequestParam(name = "movieId") Long movieId) {
        movieService.rateMovie(movieId, ratingId);
    }

    @GetMapping("findByCategory/{category}")
    public final List<Movie> findMoviesByCategory(@PathVariable String category) {
        return movieService.findMoviesByCategory(category);
    }


}
