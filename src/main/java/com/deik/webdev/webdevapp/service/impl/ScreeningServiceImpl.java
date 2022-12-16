package com.deik.webdev.webdevapp.service.impl;

import com.deik.webdev.webdevapp.entity.MovieEntity;
import com.deik.webdev.webdevapp.entity.RoomEntity;
import com.deik.webdev.webdevapp.entity.ScreeningEntity;
import com.deik.webdev.webdevapp.model.ScreeningDto;
import com.deik.webdev.webdevapp.repository.MovieRepository;
import com.deik.webdev.webdevapp.repository.RoomRepository;
import com.deik.webdev.webdevapp.repository.ScreeningRepository;
import com.deik.webdev.webdevapp.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    @Override
    public boolean createScreening(ScreeningDto screeningDto) {
        ScreeningEntity screening = screeningDtoToScreeningEntity(screeningDto);
        Date screeningStartDate = screening.getScreeningTime();
        Date screeningEndDate = addMinutesToDate(screeningStartDate, screening.getMovie().getLength());

        List<ScreeningEntity> potentiallyOverlappingScreenings = screeningRepository.findAllByRoom(screening.getRoom());

        for (ScreeningEntity screeningEntity : potentiallyOverlappingScreenings) {
            Date entityScreeningTimeStart = screeningEntity.getScreeningTime();
            Date entityScreeningTimeEnd = addMinutesToDate(entityScreeningTimeStart,
                    screeningEntity.getMovie().getLength());

            if (isIntervalOverlappingWithGivenInterval(screeningStartDate, screeningEndDate,
                   entityScreeningTimeStart, entityScreeningTimeEnd)) {
                return false;
            }
        }

        screeningRepository.save(screening);

        return true;
    }

    @Override
    public boolean deleteScreening(ScreeningDto screeningDto) {
        ScreeningEntity convertedScreening = screeningDtoToScreeningEntity(screeningDto);
        MovieEntity movie = convertedScreening.getMovie();
        ScreeningEntity screening = screeningRepository.findByMovieAndRoomAndScreeningTime(
                movie,
                convertedScreening.getRoom(),
                convertedScreening.getScreeningTime()
        ).orElse(null);

        if (Objects.isNull(movie)) {
            return false;
        }

        screeningRepository.delete(screening);

        return true;
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        Iterable<ScreeningEntity> screeningEntityList = screeningRepository.findAll();

        return screeningEntityListToScreeningDtoList(screeningEntityList);
    }

    private ScreeningEntity screeningDtoToScreeningEntity(ScreeningDto screeningDto) {
        MovieEntity movie;
        RoomEntity room;
        Date screeningTime;

        movie = movieRepository.findByTitle(screeningDto.getMovieTitle())
                .orElseThrow(() -> new IllegalArgumentException("There's no movie by that title!"));

        room = roomRepository.findByName(screeningDto.getRoomName())
                .orElseThrow(() -> new IllegalArgumentException("There's no room by that name!"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            screeningTime = formatter.parse(screeningDto.getScreeningTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new ScreeningEntity(movie, room, screeningTime);
    }

    private List<ScreeningDto> screeningEntityListToScreeningDtoList(Iterable<ScreeningEntity> screeningEntityList) {
        List<ScreeningDto> screeningDtoList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (var screeningEntity : screeningEntityList) {
            screeningDtoList.add(new ScreeningDto(
                    screeningEntity.getMovie().getTitle(),
                    screeningEntity.getRoom().getName(),
                    formatter.format(screeningEntity.getScreeningTime())
            ));
        }

        return screeningDtoList;
    }

    private Date addMinutesToDate(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);

        return cal.getTime();
    }

    private boolean isIntervalOverlappingWithGivenInterval(Date startOfFirst, Date endOfFirst,
                                                           Date startOfSecond, Date endOfSecond) {
        return !(endOfFirst.before(startOfSecond) || endOfSecond.before(startOfFirst));
    }

}