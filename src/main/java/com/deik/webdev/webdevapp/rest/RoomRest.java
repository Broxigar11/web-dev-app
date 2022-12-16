package com.deik.webdev.webdevapp.rest;

import com.deik.webdev.webdevapp.model.RoomDto;
import com.deik.webdev.webdevapp.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomRest {
    final RoomService roomService;

    @PostMapping("/create")
    ResponseEntity<String> createRoom(
            @RequestParam String name, @RequestParam int rows, @RequestParam int columns
    ) {
        if (name.isEmpty() || rows <= 0 || columns <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters");
        }
        roomService.createRoom(new RoomDto(name, rows, columns));
        return ResponseEntity.status(HttpStatus.CREATED).body("Room created");
    }

    @PostMapping("/update")
    ResponseEntity<String> updateRoom(
            @RequestParam String name, @RequestParam int rows, @RequestParam int columns
    ) {
        if (name.isEmpty() || rows <= 0 || columns <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters");
        }

        return roomService.updateRoom(new RoomDto(name, rows, columns))
                ?  ResponseEntity.status(HttpStatus.OK).body("Room updated")
                :  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Room");
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> deleteRoom(@RequestParam String name) {
        return roomService.deleteRoom(name)
                ?  ResponseEntity.status(HttpStatus.OK).body("Room deleted")
                :  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Room");
    }

    @GetMapping("/get")
    ResponseEntity<RoomDto> getRoom(@RequestParam String name) {
        RoomDto roomDto = roomService.getRoomByName(name);
        return Objects.isNull(roomDto)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
                : ResponseEntity.status(HttpStatus.OK).body(roomDto);
    }

    @GetMapping("/get_all")
    ResponseEntity<List<RoomDto>> getMovieList() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomList());
    }

}
