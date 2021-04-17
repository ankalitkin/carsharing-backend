package ru.vsu.cs.carsharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.carsharing.dto.UserEditDto;
import ru.vsu.cs.carsharing.service.ProfileEditService;

@RestController
@RequestMapping("/edit")
@RequiredArgsConstructor
public class UserEditController {
    private final ProfileEditService userService;

    @PostMapping("/")
    public void editUser(@RequestBody UserEditDto dto) {
        userService.edit(dto);
    }
}
