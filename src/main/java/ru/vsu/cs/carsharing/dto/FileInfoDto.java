package ru.vsu.cs.carsharing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileInfoDto {
    private String name;
    private String url;
    private String path;
}
