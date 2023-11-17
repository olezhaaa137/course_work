package com.example.course_work.web;

import com.example.course_work.entities.Image;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class PropertyToShowToUser {
    private Long id;

    private String numberOfRooms;

    private String square;

    private String livingSquare;

    private String kitchenSquare;

    private String yearOfConstruction;

    private String price;


    private String description;

    private String floor;


    private Image image;

    private boolean isLiked;

    public PropertyToShowToUser(Long id, String numberOfRooms, String square, String livingSquare, String kitchenSquare, String yearOfConstruction, String price, String description, String floor, Image image, boolean isLiked) {
        this.id = id;
        this.numberOfRooms = numberOfRooms;
        this.square = square;
        this.livingSquare = livingSquare;
        this.kitchenSquare = kitchenSquare;
        this.yearOfConstruction = yearOfConstruction;
        this.price = price;
        this.description = description;
        this.floor = floor;
        this.image = image;
        this.isLiked = isLiked;
    }
}
