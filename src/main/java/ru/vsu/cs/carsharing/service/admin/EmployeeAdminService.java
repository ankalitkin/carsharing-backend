package ru.vsu.cs.carsharing.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.EmployeeDao;
import ru.vsu.cs.carsharing.dto.admin.FullEmployeeDto;
import ru.vsu.cs.carsharing.entity.Employee;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeAdminService {
    private final EmployeeDao dao;
    private final EmployeeService employeeService;

    public List<Employee> getAllEmployees() {
        return dao.findAllByOrderById();
    }

    public Optional<Employee> findById(int id) {
        return dao.findById(id);
    }

    public Employee updateOrAddNew(FullEmployeeDto dto) {
        Employee employee = dao.getById(dto.getId());
        if (employee == null) {
            employee = new Employee();
        }
        employee.setName(dto.getName() != null ? dto.getName() : "");
        employee.setLogin(dto.getLogin());
        employee.setDeleted(dto.isDeleted());
        employee.setRoles(dto.getRoles() != null ? dto.getRoles() : "");
        String newPassword = dto.getNewPassword();
        if (newPassword != null) {
            if (newPassword.trim().length() == 0) {
                throw new WebException("Blank password provided", HttpStatus.BAD_REQUEST);
            }
            employeeService.setPassword(employee, newPassword);
        }
        return dao.save(employee);
    }

    public void delete(int id) {
        if (!dao.existsById(id)) {
            throw new WebException("Employee not found", HttpStatus.NOT_FOUND);
        }
        dao.deleteById(id);
    }
}

