package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class LogDto {
    private int id;
    private String type;
    private int customerId;
    private int carId;
    private String value;
}
