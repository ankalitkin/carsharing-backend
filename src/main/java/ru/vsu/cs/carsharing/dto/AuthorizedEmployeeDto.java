package ru.vsu.cs.carsharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AuthorizedEmployeeDto {
    private EmployeeDto employee;
    private String tokenData;
    private Set<String> authorities;
}
