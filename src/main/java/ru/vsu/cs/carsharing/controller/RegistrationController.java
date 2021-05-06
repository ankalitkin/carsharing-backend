package ru.vsu.cs.carsharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.carsharing.dto.AuthorizedEmployeeDto;
import ru.vsu.cs.carsharing.dto.RegistrationDataDto;
import ru.vsu.cs.carsharing.service.AuthorizationService;
import ru.vsu.cs.carsharing.service.RegistrationService;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final AuthorizationService authorizationService;

    @PostMapping("/")
    public AuthorizedEmployeeDto register(@RequestBody RegistrationDataDto dto) {
        registrationService.register(dto.getLogin(), dto.getPassword(), dto.getName());
        return authorizationService.authorizeEmployee(dto.getLogin(), dto.getPassword());
    }
}
