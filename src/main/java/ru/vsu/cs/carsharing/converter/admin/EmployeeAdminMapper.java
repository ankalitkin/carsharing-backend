package ru.vsu.cs.carsharing.converter.admin;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.admin.FullEmployeeDto;
import ru.vsu.cs.carsharing.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeAdminMapper { //NOSONAR
    EmployeeAdminMapper INSTANCE = Mappers.getMapper(EmployeeAdminMapper.class);

    FullEmployeeDto toDto(Employee entity);

    Employee toEntity(FullEmployeeDto dto);

    List<FullEmployeeDto> toDtoList(List<Employee> entities);

    List<Employee> toEntityList(List<FullEmployeeDto> dtos);
}

