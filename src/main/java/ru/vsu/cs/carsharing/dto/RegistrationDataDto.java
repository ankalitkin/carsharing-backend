package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class RegistrationDataDto {
    private String name;
    private String login;
    private String password;
}
