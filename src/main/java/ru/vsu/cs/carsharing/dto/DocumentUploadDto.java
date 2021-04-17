package ru.vsu.cs.carsharing.dto;

import lombok.Data;

@Data
public class DocumentUploadDto {
    private int id;
    private int customerId;
    private String filename;
}
