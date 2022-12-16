package com.deik.webdev.webdevapp.repository;

import com.deik.webdev.webdevapp.entity.MovieEntity;
import com.deik.webdev.webdevapp.entity.RoomEntity;
import com.deik.webdev.webdevapp.entity.ScreeningEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends CrudRepository<ScreeningEntity, String> {

    Optional<ScreeningEntity> findByMovieAndRoomAndScreeningTime(MovieEntity movie, RoomEntity room, Date date);

    List<ScreeningEntity> findAllByRoom(RoomEntity room);

}
