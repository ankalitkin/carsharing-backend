package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.EmployeeDao;
import ru.vsu.cs.carsharing.entity.Employee;
import ru.vsu.cs.carsharing.exception.WebException;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final EmployeeDao employeeDao;

    //To be deleted
    public Employee register(String login, String password, String name) {
        if (employeeDao.existsByLogin(login)) {
            throw new WebException("Login is already used", HttpStatus.BAD_REQUEST);
        }
        String salt = HashUtills.generateSalt(10);
        password = HashUtills.hashPassword(password, salt);
        Employee employee = new Employee(login, password, salt, name, "");
        employeeDao.save(employee);
        return employee;
    }
}
