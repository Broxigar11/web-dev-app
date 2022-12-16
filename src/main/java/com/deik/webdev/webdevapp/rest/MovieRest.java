package com.deik.webdev.webdevapp.rest;

import com.deik.webdev.webdevapp.model.MovieDto;
import com.deik.webdev.webdevapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieRest {

    final MovieService movieService;

    @PostMapping("/create")
    ResponseEntity<String> createMovie(
            @RequestParam String title, @RequestParam String genre, @RequestParam int length
    ) {
        if (title.isEmpty() && genre.isEmpty() && length <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters");
        }

        movieService.createMovie(new MovieDto(title, genre, length));
        return ResponseEntity.status(HttpStatus.CREATED).body("Movie created");
    }

    @PutMapping("/update")
    ResponseEntity<String> updateMovie(
            @RequestParam String title, @RequestParam String genre, @RequestParam int length
    ) {
        if (title.isEmpty() && genre.isEmpty() && length <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters");
        }

        return movieService.updateMovie(new MovieDto(title, genre, length))
                ?  ResponseEntity.status(HttpStatus.OK).body("Movie updated")
                :  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such movie");
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> deleteMovie(@RequestParam String title) {
        return movieService.deleteMovie(title)
                ?  ResponseEntity.status(HttpStatus.OK).body("Movie deleted")
                :  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such movie");
    }

    @GetMapping("/get")
    ResponseEntity<MovieDto> getMovie(@RequestParam String title) {
        MovieDto movieDto = movieService.getMovieByTitle(title);
        return Objects.isNull(movieDto)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
                : ResponseEntity.status(HttpStatus.OK).body(movieDto);
    }

    @GetMapping("/get_all")
    ResponseEntity<List<MovieDto>> getMovieList() {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieList());
    }

}
