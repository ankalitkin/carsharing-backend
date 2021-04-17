package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.CarDto;
import ru.vsu.cs.carsharing.entity.Car;

import java.util.List;

@Mapper
public interface CarMapper { //NOSONAR
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDto toDto(Car entity);

    Car toEntity(CarDto dto);

    List<CarDto> toDtoList(List<Car> entities);

    List<Car> toEntityList(List<CarDto> dtos);
}

