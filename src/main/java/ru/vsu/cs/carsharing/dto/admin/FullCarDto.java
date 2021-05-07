package ru.vsu.cs.carsharing.dto.admin;

import lombok.Data;

@Data
public class FullCarDto {
    private Integer id;
    private String model;
    private String vin;
    private double latitude;
    private double longitude;
    private boolean used;
    private boolean visible;
    private String comment;
}
