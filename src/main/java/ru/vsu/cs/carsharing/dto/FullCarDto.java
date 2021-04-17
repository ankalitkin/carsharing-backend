package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class FullCarDto {
    private int id;
    private String model;
    private String vin;
    private double latitude;
    private double longitude;
    private boolean isUsed;
    private boolean isVisible;
    private String comment;
}
