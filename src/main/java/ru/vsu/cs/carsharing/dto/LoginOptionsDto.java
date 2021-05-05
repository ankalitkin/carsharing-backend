package ru.vsu.cs.carsharing.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginOptionsDto {
    @NotBlank(message = "Login must not be blank")
    private String login;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
