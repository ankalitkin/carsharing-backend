package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.FullCarDto;
import ru.vsu.cs.carsharing.entity.Car;

import java.util.List;

@Mapper
public interface FullCarMapper { //NOSONAR
    FullCarMapper INSTANCE = Mappers.getMapper(FullCarMapper.class);

    FullCarDto toDto(Car entity);

    Car toEntity(FullCarDto dto);

    List<FullCarDto> toDtoList(List<Car> entities);

    List<Car> toEntityList(List<FullCarDto> dtos);
}

