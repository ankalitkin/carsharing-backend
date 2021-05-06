package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.EmployeeDto;
import ru.vsu.cs.carsharing.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper { //NOSONAR
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toDto(Employee entity);

    Employee toEntity(EmployeeDto dto);

    List<EmployeeDto> toDtoList(List<Employee> entities);

    List<Employee> toEntityList(List<EmployeeDto> dtos);
}

