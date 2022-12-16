package com.deik.webdev.webdevapp.repository;

import com.deik.webdev.webdevapp.entity.RoomEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, String> {

    Optional<RoomEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update RoomEntity r set r.rows = :rows, r.columns = :columns where r.name = :name")
    void updateRoomEntity(String name, Integer rows, Integer columns);

}
