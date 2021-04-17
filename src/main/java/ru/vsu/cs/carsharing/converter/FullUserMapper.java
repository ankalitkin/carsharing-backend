package ru.vsu.cs.carsharing.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.cs.carsharing.dto.FullUserDto;
import ru.vsu.cs.carsharing.entity.User;

import java.util.List;

@Mapper
public interface FullUserMapper { //NOSONAR
    FullUserMapper INSTANCE = Mappers.getMapper(FullUserMapper.class);

    FullUserDto toDto(User entity);

    User toEntity(FullUserDto dto);

    List<FullUserDto> toDtoList(List<User> entities);

    List<User> toEntityList(List<FullUserDto> dtos);
}

