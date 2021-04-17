package ru.vsu.cs.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.carsharing.entity.User;

import java.util.List;

public interface UserDao extends CrudRepository<User, Integer> {
    boolean existsByLogin(String login);

    User getByLogin(String login);

    User getById(int id);

    List<User> findAllByIdIn(List<Integer> ids);
}
