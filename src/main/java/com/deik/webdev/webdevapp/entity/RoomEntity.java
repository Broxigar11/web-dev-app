package com.deik.webdev.webdevapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rooms")
public class RoomEntity {

    public RoomEntity(String name, Integer rows, Integer columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(name = "room_rows")
    private Integer rows;

    @Column(name = "room_columns")
    private Integer columns;

}
