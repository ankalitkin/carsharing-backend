package ru.vsu.cs.carsharing.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DocumentDto {
    private int id;
    private int customerId;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private Date issuedAt;
    private Date validBy;
    private Date drivingExpSince;
    private String photoFilename;
}
