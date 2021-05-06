package ru.vsu.cs.carsharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.carsharing.converter.EmployeeMapper;
import ru.vsu.cs.carsharing.dto.EmployeeDto;
import ru.vsu.cs.carsharing.dto.UpdateEmployeeDto;
import ru.vsu.cs.carsharing.service.EmployeeService;

@RestController
@RequestMapping("/employeeProfile")
@RequiredArgsConstructor
public class CurrentEmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("")
    public EmployeeDto getCurrentEmployee() {
        return EmployeeMapper.INSTANCE.toDto(employeeService.getCurrentEmployee());
    }

    @PostMapping("")
    public EmployeeDto updateProfile(@RequestBody UpdateEmployeeDto dto) {
        employeeService.updateProfile(dto);
        return getCurrentEmployee();
    }

}
