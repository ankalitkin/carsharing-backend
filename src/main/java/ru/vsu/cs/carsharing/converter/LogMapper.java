package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.LogDto;
import ru.vsu.cs.carsharing.entity.Log;

import java.util.List;

@Mapper
public interface LogMapper { //NOSONAR
    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    LogDto toDto(Log entity);

    Log toEntity(LogDto dto);

    List<LogDto> toDtoList(List<Log> entities);

    List<Log> toEntityList(List<LogDto> dtos);
}

