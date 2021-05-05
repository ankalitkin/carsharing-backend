package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.UserDao;
import ru.vsu.cs.carsharing.entity.User;
import ru.vsu.cs.carsharing.exception.WebException;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserDao userDao;

    //To be deleted
    public User register(String login, String password, String name) {
        if (userDao.existsByLogin(login)) {
            throw new WebException("Login is already used", HttpStatus.BAD_REQUEST);
        }
        String salt = HashUtills.generateSalt(10);
        password = HashUtills.hashPassword(password, salt);
        User user = new User(login, password, salt, name, "");
        userDao.save(user);
        return user;
    }
}
