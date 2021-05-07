package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.EmployeeDao;
import ru.vsu.cs.carsharing.entity.Employee;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitializingService {
    public static final String DEFAULT_ADMIN_LOGIN = System.getenv("DEFAULT_ADMIN_LOGIN");
    public static final String DEFAULT_ADMIN_PASSWORD = System.getenv("DEFAULT_ADMIN_PASSWORD");
    private final EmployeeDao employeeDao;
    private final EmployeeService employeeService;

    public void init() {
        if (!employeeDao.existsByDeleted(false)) {
            log.warn("Activating default admin");
            Employee employee = employeeDao.getByLogin(DEFAULT_ADMIN_LOGIN);
            if (employee == null) {
                employee = new Employee();
            }
            employee.setLogin(DEFAULT_ADMIN_LOGIN);
            employee.setName("DEFAULT ADMIN");
            employee.setRoles("DEFAULT");
            employee.setDeleted(false);
            employeeService.setPassword(employee, DEFAULT_ADMIN_PASSWORD);
            employeeDao.save(employee);
        }
    }
}
