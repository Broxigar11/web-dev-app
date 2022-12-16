package com.deik.webdev.webdevapp.service;

import com.deik.webdev.webdevapp.model.ScreeningDto;

import java.util.List;

public interface ScreeningService {

    boolean createScreening(ScreeningDto screeningDto);

    boolean deleteScreening(ScreeningDto screeningDto);

    List<ScreeningDto> getScreeningList();


}
