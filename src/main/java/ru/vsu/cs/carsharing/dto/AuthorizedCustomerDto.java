package ru.vsu.cs.carsharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizedCustomerDto {
    private CustomerDto customer;
    private String tokenData;
}
