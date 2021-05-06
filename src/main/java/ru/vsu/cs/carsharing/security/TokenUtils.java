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
            String[] split = subject.split(" ");
            return split.length > 1 ? split[1] : null;
        } catch (Exception ex) {
            throw new WebException("Wrong token data", ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
