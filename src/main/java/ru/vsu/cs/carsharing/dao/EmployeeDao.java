package ru.vsu.cs.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.carsharing.entity.Employee;

import java.util.List;

public interface EmployeeDao extends CrudRepository<Employee, Integer> {
    List<Employee> findAllByOrderById();

    boolean existsByLogin(String login);

    boolean existsByLoginAndDeleted(String login, boolean deleted);

    Employee getByLogin(String login);

    Employee getById(int id);

    boolean existsByDeleted(boolean deleted);
}
