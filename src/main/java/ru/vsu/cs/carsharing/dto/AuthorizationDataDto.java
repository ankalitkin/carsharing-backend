package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class AuthorizationDataDto {
    private String login;
    private String password;
}
