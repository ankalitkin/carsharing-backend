package ru.vsu.cs.carsharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.carsharing.dto.AuthorizedCustomerDto;
import ru.vsu.cs.carsharing.dto.AuthorizedEmployeeDto;
import ru.vsu.cs.carsharing.dto.CustomerLoginOptionsDto;
import ru.vsu.cs.carsharing.dto.LoginOptionsDto;
import ru.vsu.cs.carsharing.dto.SMSRequestDto;
import ru.vsu.cs.carsharing.service.AuthorizationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("/pass")
    public AuthorizedEmployeeDto authEmployee(@Valid @RequestBody LoginOptionsDto loginOptionsDto) {
        return authorizationService.authorizeEmployee(loginOptionsDto.getLogin(), loginOptionsDto.getPassword());
    }

    @PostMapping("/sendSMS")
    public void authCustomer(@Valid @RequestBody SMSRequestDto smsRequestDto) {
        authorizationService.sendCustomerSMS(smsRequestDto.getPhoneNumber());
    }

    @PostMapping("/authSMS")
    public AuthorizedCustomerDto authCustomer(@Valid @RequestBody CustomerLoginOptionsDto optionsDto) {
        return authorizationService.authorizeCustomer(optionsDto.getPhoneNumber(), optionsDto.getCode());
    }
}
