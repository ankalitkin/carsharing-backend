package ru.vsu.cs.carsharing.converter.admin;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.admin.FullCarDto;
import ru.vsu.cs.carsharing.entity.Car;

import java.util.List;

@Mapper
public interface CarAdminMapper { //NOSONAR
    CarAdminMapper INSTANCE = Mappers.getMapper(CarAdminMapper.class);

    FullCarDto toDto(Car entity);

    Car toEntity(FullCarDto dto);

    List<FullCarDto> toDtoList(List<Car> entities);

    List<Car> toEntityList(List<FullCarDto> dtos);
}

