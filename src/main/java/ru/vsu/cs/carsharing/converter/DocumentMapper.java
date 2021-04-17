package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.DocumentDto;
import ru.vsu.cs.carsharing.entity.Document;

import java.util.List;

@Mapper
public interface DocumentMapper { //NOSONAR
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentDto toDto(Document entity);

    Document toEntity(DocumentDto dto);

    List<DocumentDto> toDtoList(List<Document> entities);

    List<Document> toEntityList(List<DocumentDto> dtos);
}

