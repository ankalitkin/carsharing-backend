package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.EmployeeDao;
import ru.vsu.cs.carsharing.dto.UpdateEmployeeDto;
import ru.vsu.cs.carsharing.entity.Employee;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.security.CurrentUserService;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeDao employeeDao;

    public Employee getCurrentEmployee() {
        int id = CurrentUserService.getEmployeeId();
        return employeeDao.getById(id);
    }

    public void updateProfile(UpdateEmployeeDto dto) {
        String name = dto.getName();
        String login = dto.getLogin();
        String newPassword = dto.getNewPassword();
        String oldPassword = dto.getOldPassword();

        Employee employee = employeeDao.getById(CurrentUserService.getEmployeeId());
        if (!login.equals(employee.getLogin()) && employeeDao.existsByLogin(login)) {
            throw new WebException("Login is already used", HttpStatus.CONFLICT);
        }
        employee.setLogin(login);
        employee.setName(name);
        if (oldPassword != null && oldPassword.length() != 0 && newPassword != null && newPassword.length() != 0) {
            String oldHashedPassword = HashUtills.hashPassword(oldPassword, employee.getSalt());
            if (!employee.getPassword().equals(oldHashedPassword)) {
                throw new WebException("Wrong password", HttpStatus.BAD_REQUEST);
            }
            String newHashedPassword = HashUtills.hashPassword(newPassword, employee.getSalt());
            employee.setPassword(newHashedPassword);
        }
        employeeDao.save(employee);
    }

}
