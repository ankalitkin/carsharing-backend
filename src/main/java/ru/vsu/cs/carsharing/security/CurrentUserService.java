package ru.vsu.cs.carsharing.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrentUserService {

    public static int getUserId() {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return TokenUtils.getUserId(subject);
    }

    public static String getLogin() {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return TokenUtils.getLogin(subject);
    }
}
