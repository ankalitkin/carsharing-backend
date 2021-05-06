package ru.vsu.cs.carsharing.security;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserDetails {
    int userId;
    String login;
    String audience;
}
