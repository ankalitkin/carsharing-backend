package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.converter.UserMapper;
import ru.vsu.cs.carsharing.dao.UserDao;
import ru.vsu.cs.carsharing.dto.AuthorizedUserDto;
import ru.vsu.cs.carsharing.dto.UserDto;
import ru.vsu.cs.carsharing.entity.User;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.security.JwtTokenProvider;
import ru.vsu.cs.carsharing.security.Keychain;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final JwtTokenProvider jwtTokenProvider;
    private final Keychain keychain;
    private final UserDao userDao;

    public AuthorizedUserDto authorizeUser(String login, String password) {
        try {
            User authorizedUser = authUser(login, password);
            UserDto dto = UserMapper.INSTANCE.toDto(authorizedUser);
            int userId = authorizedUser.getId();
            String tokenLogin = String.format("%d %s", userId, login);
            String token = jwtTokenProvider.createToken(tokenLogin);
            List<String> authorities = keychain.getAuthoritiesStrings(login, userId);
            return new AuthorizedUserDto(dto, JwtTokenProvider.BEARER + token, authorities, null);
        } catch (Exception e) {
            log.error("Error during authorization", e);
            throw e;
        }
    }

    private User authUser(String login, String password) {
        if (!userDao.existsByLogin(login)) {
            throw new WebException("Login doesn't exist", HttpStatus.UNAUTHORIZED);
        }
        User user = userDao.getByLogin(login);
        if (!Objects.equals(HashUtills.hashPassword(password, user.getSalt()), user.getPassword())) {
            throw new WebException("Wrong password", HttpStatus.FORBIDDEN);
        }
        return user;
    }

}
