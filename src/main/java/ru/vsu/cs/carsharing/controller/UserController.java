package ru.vsu.cs.carsharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.carsharing.converter.UserMapper;
import ru.vsu.cs.carsharing.dto.UserDto;
import ru.vsu.cs.carsharing.entity.User;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/id/{id}")
    public UserDto getUserById(@PathVariable int id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new WebException(String.format("User with id %d not found", id), HttpStatus.NOT_FOUND);
        }
        return UserMapper.INSTANCE.toDto(user);
    }

    @GetMapping("/login/{login}")
    public UserDto getUserByLogin(@PathVariable String login) {
        User user = userService.getByLogin(login);
        if (user == null) {
            throw new WebException(String.format("User with login %s not found", login), HttpStatus.NOT_FOUND);
        }
        return UserMapper.INSTANCE.toDto(user);
    }

    @PostMapping("/ids")
    public List<UserDto> getListByIds(@Valid @RequestBody List<Integer> ids) {
        List<User> users = userService.findUsersByIdList(ids);
        return UserMapper.INSTANCE.toDtoList(users);
    }

}
