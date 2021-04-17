package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.UserDao;
import ru.vsu.cs.carsharing.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    public List<User> findUsersByIdList(List<Integer> ids) {
        return userDao.findAllByIdIn(ids);
    }

}
