package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.FullEmployeeDto;
import ru.vsu.cs.carsharing.entity.Employee;

import java.util.List;

@Mapper
public interface FullEmployeeMapper { //NOSONAR
    FullEmployeeMapper INSTANCE = Mappers.getMapper(FullEmployeeMapper.class);

    FullEmployeeDto toDto(Employee entity);

    Employee toEntity(FullEmployeeDto dto);

    List<FullEmployeeDto> toDtoList(List<Employee> entities);

    List<Employee> toEntityList(List<FullEmployeeDto> dtos);
}

