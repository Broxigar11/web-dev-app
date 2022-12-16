package com.deik.webdev.webdevapp.service.impl;

import com.deik.webdev.webdevapp.entity.MovieEntity;
import com.deik.webdev.webdevapp.model.MovieDto;
import com.deik.webdev.webdevapp.repository.MovieRepository;
import com.deik.webdev.webdevapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public void createMovie(MovieDto movieDto) {
        movieRepository.save(new MovieEntity(
                    movieDto.getTitle(),
                    movieDto.getGenre(),
                    movieDto.getLength()
        ));
    }

    @Override
    public boolean updateMovie(MovieDto movieDto) {
        MovieEntity movie = movieRepository.findByTitle(movieDto.getTitle())
                .orElse(null);
        if (Objects.isNull(movie)) {
            return false;
        }

        movieRepository.updateMovieEntity(
                movieDto.getTitle(),
                movieDto.getGenre(),
                movieDto.getLength()
        );

        return true;
    }

    @Override
    public boolean deleteMovie(String title) {
        MovieEntity movie = movieRepository.findByTitle(title)
                .orElse(null);
        if (Objects.isNull(movie)) {
            return false;
        }

        movieRepository.delete(movie);

        return true;
    }

    @Override
    public MovieDto getMovieByTitle(String title) {
        Optional<MovieEntity> movieEntity = movieRepository.findByTitle(title);

        if (movieEntity.isEmpty()) {
            return null;
        }

        MovieEntity movie = movieEntity.get();
        return new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength());
    }

    @Override
    public List<MovieDto> getMovieList() {
        Iterable<MovieEntity> movieList = movieRepository.findAll();

        return movieEntityListToMovieDtoList(movieList);
    }

    private List<MovieDto> movieEntityListToMovieDtoList(Iterable<MovieEntity> movieEntityList) {
        List<MovieDto> movieDtoList = new ArrayList<>();

        for (MovieEntity entity : movieEntityList) {
            movieDtoList.add(new MovieDto(
                    entity.getTitle(),
                    entity.getGenre(),
                    entity.getLength()
            ));
        }
        return movieDtoList;
    }
}
