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
        Employee employee = employeeDao.getById(CurrentUserService.getEmployeeId());
        employee.setName(dto.getName());
        tryChangeLogin(employee, dto.getLogin());
        tryChangePassword(employee, dto.getOldPassword(), dto.getNewPassword());
        employeeDao.save(employee);
    }

    private void tryChangeLogin(Employee employee, String login) {
        if (!login.equals(employee.getLogin()) && employeeDao.existsByLogin(login)) {
            throw new WebException("Login is already used", HttpStatus.CONFLICT);
        }
        employee.setLogin(login);
    }

    public void tryChangePassword(Employee employee, String oldPassword, String newPassword) {
        if (oldPassword != null && oldPassword.length() != 0 && newPassword != null && newPassword.length() != 0) {
            String oldHashedPassword = HashUtills.hashPassword(oldPassword, employee.getSalt());
            if (!employee.getPassword().equals(oldHashedPassword)) {
                throw new WebException("Wrong password", HttpStatus.BAD_REQUEST);
            }
            setPassword(employee, newPassword);
        }
    }


    public void setPassword(Employee employee, String newPassword) {
        String salt = HashUtills.generateSalt(10);
        String newHashedPassword = HashUtills.hashPassword(newPassword, salt);
        employee.setSalt(salt);
        employee.setPassword(newHashedPassword);
    }

}
