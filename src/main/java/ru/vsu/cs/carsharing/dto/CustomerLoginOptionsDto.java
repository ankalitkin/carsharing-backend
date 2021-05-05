package ru.vsu.cs.carsharing.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerLoginOptionsDto {
    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;

    @NotBlank(message = "Code must not be blank")
    private String code;

}
