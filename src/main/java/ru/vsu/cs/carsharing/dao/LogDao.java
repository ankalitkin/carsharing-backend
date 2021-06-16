package ru.vsu.cs.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.carsharing.entity.Log;

import java.util.List;

public interface LogDao extends CrudRepository<Log, Integer> {
    List<Log> findAllByOrderById();
}
