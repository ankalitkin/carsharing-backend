package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.UserDao;
import ru.vsu.cs.carsharing.dto.UserEditDto;
import ru.vsu.cs.carsharing.entity.User;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.security.CurrentUserService;

@Service
@RequiredArgsConstructor
public class ProfileEditService {
    private final UserDao userDao;

    public void edit(UserEditDto dto) {
        int id = dto.getId();
        String name = dto.getName();
        String login = dto.getLogin();
        String newPassword = dto.getNewPassword();
        String oldPassword = dto.getOldPassword();

        if (CurrentUserService.getUserId() != id) {
            throw new WebException("Wrong id", HttpStatus.FORBIDDEN);
        }

        User user = userDao.getById(id);
        if (!login.equals(user.getLogin()) && userDao.existsByLogin(login)) {
            throw new WebException("Login is already used", HttpStatus.CONFLICT);
        }
        user.setLogin(login);
        user.setName(name);
        if (oldPassword != null && oldPassword.length() != 0 && newPassword != null && newPassword.length() != 0) {
            String oldHashedPassword = HashUtills.hashPassword(oldPassword, user.getSalt());
            if (!user.getPassword().equals(oldHashedPassword)) {
                throw new WebException("Wrong password", HttpStatus.BAD_REQUEST);
            }
            String newHashedPassword = HashUtills.hashPassword(newPassword, user.getSalt());
            user.setPassword(newHashedPassword);
        }
        userDao.save(user);
    }
}
