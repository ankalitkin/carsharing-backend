package ru.vsu.cs.carsharing.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {
    private int id;
    private String name;
    private String phoneNumber;
    private String selfieFilename;
    private boolean profileConfirmed;
    private Date bannedUntil;
}
