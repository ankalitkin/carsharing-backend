package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class UpdateEmployeeDto {
    private String login;
    private String name;
    private String newPassword;
    private String oldPassword;
}
