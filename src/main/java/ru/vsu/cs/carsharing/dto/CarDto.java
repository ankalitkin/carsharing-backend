package ru.vsu.cs.carsharing.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarDto {
    private int id;
    private String model;
    private double latitude;
    private double longitude;
    private List<String> photoFilenames;
}
