package ru.vsu.cs.carsharing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthorizedUserDto {
    @JsonProperty("user")
    private UserDto userDto;
    private String tokenData;
    private List<String> authorities;
    private Integer targetId;
}
