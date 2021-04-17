package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class CarDto {
    private int id;
    private String model;
    private double latitude;
    private double longitude;
}
