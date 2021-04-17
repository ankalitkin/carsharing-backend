package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.DocumentUploadDto;
import ru.vsu.cs.carsharing.entity.DocumentUpload;

import java.util.List;

@Mapper
public interface DocumentUploadMapper { //NOSONAR
    DocumentUploadMapper INSTANCE = Mappers.getMapper(DocumentUploadMapper.class);

    DocumentUploadDto toDto(DocumentUpload entity);

    DocumentUpload toEntity(DocumentUploadDto dto);

    List<DocumentUploadDto> toDtoList(List<DocumentUpload> entities);

    List<DocumentUpload> toEntityList(List<DocumentUploadDto> dtos);
}

