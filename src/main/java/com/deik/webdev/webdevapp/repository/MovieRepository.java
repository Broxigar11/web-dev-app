package com.deik.webdev.webdevapp.repository;


import com.deik.webdev.webdevapp.entity.MovieEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {

    Optional<MovieEntity> findByTitle(String title);

    @Transactional
    @Modifying
    @Query("update MovieEntity m set m.genre = :genre, m.length = :length where m.title = :title")
    void updateMovieEntity(String title, String genre, Integer length);

}
