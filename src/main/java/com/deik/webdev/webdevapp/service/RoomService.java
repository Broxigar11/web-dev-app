package com.deik.webdev.webdevapp.service;

import com.deik.webdev.webdevapp.model.RoomDto;

import java.util.List;

public interface RoomService {

    void createRoom(RoomDto roomDto);

    boolean updateRoom(RoomDto roomDto);

    boolean deleteRoom(String name);

    RoomDto getRoomByName(String name);

    List<RoomDto> getRoomList();

}
