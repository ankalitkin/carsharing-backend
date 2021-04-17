package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String name;
    private String roles;
}
