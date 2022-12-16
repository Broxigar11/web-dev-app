package com.deik.webdev.webdevapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "screenings")
public class ScreeningEntity {

    public ScreeningEntity(MovieEntity movie, RoomEntity room, Date screeningTime) {
        this.movie = movie;
        this.room = room;
        this.screeningTime = screeningTime;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MovieEntity movie;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoomEntity room;

    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    @Column(name = "screening_time")
    private Date screeningTime;

}
