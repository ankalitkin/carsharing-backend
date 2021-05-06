package ru.vsu.cs.carsharing.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.service.AuthorizationService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrentUserService {

    private static UserDetails getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new WebException("Unsupported principal", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return (UserDetails) principal;
    }

    public static int getEmployeeId() {
        UserDetails principal = getPrincipal();
        if (!principal.getAudience().equals(AuthorizationService.EMPLOYEE)) {
            throw new WebException("User is not an employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return principal.getUserId();
    }

    public static int getCustomerId() {
        UserDetails principal = getPrincipal();
        if (!principal.getAudience().equals(AuthorizationService.CUSTOMER)) {
            throw new WebException("User is not a customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return principal.getUserId();
    }
}
