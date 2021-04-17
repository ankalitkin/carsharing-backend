package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.CustomerDto;
import ru.vsu.cs.carsharing.entity.Customer;

import java.util.List;

@Mapper
public interface CustomerMapper { //NOSONAR
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto toDto(Customer entity);

    Customer toEntity(CustomerDto dto);

    List<CustomerDto> toDtoList(List<Customer> entities);

    List<Customer> toEntityList(List<CustomerDto> dtos);
}

