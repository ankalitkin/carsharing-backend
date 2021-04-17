package ru.vsu.cs.carsharing.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.vsu.cs.carsharing.exception.WebException;

@NoArgsConstructor(access = AccessLevel.NONE)
class TokenUtils {
    static int getUserId(String subject) {
        try {
            return Integer.parseInt(subject.split(" ")[0]);
        } catch (Exception ex) {
            throw new WebException("Wrong token data", ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    static String getLogin(String subject) {
        try {
            return subject.split(" ")[1];
        } catch (Exception ex) {
            throw new WebException("Wrong token data", ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
