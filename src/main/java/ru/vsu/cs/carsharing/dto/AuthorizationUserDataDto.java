package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class AuthorizationUserDataDto {
    private String login;
    private String password;
}
