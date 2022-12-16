package com.deik.webdev.webdevapp.service.impl;

import com.deik.webdev.webdevapp.entity.RoomEntity;
import com.deik.webdev.webdevapp.model.RoomDto;
import com.deik.webdev.webdevapp.repository.RoomRepository;
import com.deik.webdev.webdevapp.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public void createRoom(RoomDto roomDto) {
        roomRepository.save(new RoomEntity(
                roomDto.getName(),
                roomDto.getRows(),
                roomDto.getColumns()
        ));
    }

    @Override
    public boolean updateRoom(RoomDto roomDto) {
        RoomEntity room = roomRepository.findByName(roomDto.getName())
                .orElse(null);
        if (Objects.isNull(room)) {
            return false;
        }

        roomRepository.updateRoomEntity(
                roomDto.getName(),
                roomDto.getRows(),
                roomDto.getColumns()
        );

        return true;
    }

    @Override
    public boolean deleteRoom(String name) {
        RoomEntity room = roomRepository.findByName(name)
                .orElse(null);
        if (Objects.isNull(room)) {
            return false;
        }

        roomRepository.delete(room);

        return true;
    }

    @Override
    public RoomDto getRoomByName(String name) {
        Optional<RoomEntity> roomEntity = roomRepository.findByName(name);

        if (roomEntity.isEmpty()) {
            return null;
        }

        RoomEntity room = roomEntity.get();
        return new RoomDto(room.getName(), room.getRows(), room.getColumns());
    }

    @Override
    public List<RoomDto> getRoomList() {
        Iterable<RoomEntity> roomList = roomRepository.findAll();

        return roomEntityListToRoomDtoList(roomList);
    }

    private List<RoomDto> roomEntityListToRoomDtoList(Iterable<RoomEntity> roomEntityList) {
        List<RoomDto> roomDtoList = new ArrayList<>();

        for (RoomEntity entity : roomEntityList) {
            roomDtoList.add(new RoomDto(
                    entity.getName(),
                    entity.getRows(),
                    entity.getColumns()
            ));
        }
        return roomDtoList;
    }
}
