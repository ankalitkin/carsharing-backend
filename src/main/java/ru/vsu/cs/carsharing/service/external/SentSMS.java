package ru.vsu.cs.carsharing.service.external;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SentSMS {
    private String phoneNumber;
    private String code;
    private LocalDateTime sentAt;
}
