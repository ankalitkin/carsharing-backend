package ru.vsu.cs.carsharing.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SMSRequestDto {
    @NotEmpty
    private String phoneNumber;
}
