package ru.vsu.cs.carsharing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizedCustomerDto {
    @JsonProperty("user")
    private CustomerDto customerDto;
    private String tokenData;
}
