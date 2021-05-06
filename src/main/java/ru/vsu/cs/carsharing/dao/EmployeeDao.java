package ru.vsu.cs.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.carsharing.entity.Employee;

import java.util.List;

public interface EmployeeDao extends CrudRepository<Employee, Integer> {
    boolean existsByLogin(String login);

    Employee getByLogin(String login);

    Employee getById(int id);

    List<Employee> findAllByIdIn(List<Integer> ids);
}
