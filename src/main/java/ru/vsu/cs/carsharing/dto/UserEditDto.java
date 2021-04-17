package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class UserEditDto {
    private int id;
    private String login;
    private String name;
    private String about;
    private String oldPassword;
    private String newPassword;
}
