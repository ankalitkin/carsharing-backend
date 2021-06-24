package ru.vsu.cs.carsharing.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogDto {
    private Integer id;
    private String type;
    private int customerId;
    private int carId;
    private String value;
    private LocalDateTime timestamp;
}
