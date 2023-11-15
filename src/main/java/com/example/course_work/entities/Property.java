package com.example.course_work.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numberOfRooms;

    private String square;

    private String livingSquare;

    private String kitchenSquare;

    private String yearOfConstruction;

    private String price;

    @Column(length = 10000)
    private String description;

    private String floor;

    @OneToMany
    private Set<Image> images;


}
