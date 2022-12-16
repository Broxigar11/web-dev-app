package com.deik.webdev.webdevapp.rest;

import com.deik.webdev.webdevapp.model.RoomDto;
import com.deik.webdev.webdevapp.model.ScreeningDto;
import com.deik.webdev.webdevapp.service.ScreeningService;
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

@RestController
@RequestMapping("/screening")
@RequiredArgsConstructor
public class ScreeningRest {

    final ScreeningService screeningService;

    @PostMapping("/create")
    ResponseEntity<String> createScreening(
            @RequestParam String movieTitle, @RequestParam String roomName, @RequestParam String screeningTime
    ) {
        if (movieTitle.isEmpty() || roomName.isEmpty() || screeningTime.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters");
        }

        return screeningService.createScreening(new ScreeningDto(movieTitle, roomName, screeningTime))
                ? ResponseEntity.status(HttpStatus.CREATED).body("Screening created")
                : ResponseEntity.status(HttpStatus.CONFLICT).body("There  is an overlapping screening");
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> deleteScreening(
            @RequestParam String movieTitle, @RequestParam String roomName, @RequestParam String screeningTime
    ) {
        if (movieTitle.isEmpty() || roomName.isEmpty() || screeningTime.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters");
        }
        return screeningService.deleteScreening(new ScreeningDto(movieTitle, roomName, screeningTime))
                ?  ResponseEntity.status(HttpStatus.OK).body("Screening deleted")
                :  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Screening");
    }


    @GetMapping("/get_all")
    ResponseEntity<List<ScreeningDto>> getScreeningList() {
        return ResponseEntity.status(HttpStatus.OK).body(screeningService.getScreeningList());
    }

}
