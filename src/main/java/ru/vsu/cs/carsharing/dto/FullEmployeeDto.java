package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class FullEmployeeDto {
    private int id;
    private String name;
    private String roles;
    private String login;
    private boolean deleted;
}
