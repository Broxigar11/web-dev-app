package com.deik.webdev.webdevapp.service;

import com.deik.webdev.webdevapp.model.MovieDto;

import java.util.List;

public interface MovieService {

    void createMovie(MovieDto movieDto);

    boolean updateMovie(MovieDto movieDto);

    boolean deleteMovie(String title);

    MovieDto getMovieByTitle(String title);

    List<MovieDto> getMovieList();

}
