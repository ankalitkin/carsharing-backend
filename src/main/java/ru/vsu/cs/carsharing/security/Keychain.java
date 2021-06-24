package ru.vsu.cs.carsharing.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.EmployeeDao;
import ru.vsu.cs.carsharing.service.AuthorizationService;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Keychain {
    public static final String ROLE_EMPLOYEE = "Employee";
    public static final String ROLE_ADMIN = "Admin";
    static final String JWT_KEY = System.getenv("JWT_KEY");
    static final Set<String> admins = getEnvAdmins();
    private final EmployeeDao employeeDao;

    private static Set<String> getEnvAdmins() {
        String str = System.getenv("ADMINS");
        if (str == null) {
            return Collections.emptySet();
        }
        String[] logins = str.split("[ ,;]");
        return Arrays.stream(logins)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }

    public Set<GrantedAuthority> getAuthorities(UserDetails details) {
        switch (details.getAudience()) {
            case AuthorizationService.CUSTOMER:
                return Collections.singleton(() -> AuthorizationService.CUSTOMER);
            case AuthorizationService.EMPLOYEE:
                return getEmployeeAuthorities(details.getLogin(), details.getUserId());
            default:
                return Collections.emptySet();
        }
    }

    public Set<GrantedAuthority> getEmployeeAuthorities(String login, int userId) {
        return getEmployeeAuthoritiesStrings(login, userId).stream()
                .map(s -> (GrantedAuthority) (() -> s))
                .collect(Collectors.toSet());
    }

    public Set<String> getEmployeeAuthoritiesStrings(String login, int userId) {
        List<String> roles = Arrays.asList(employeeDao.getById(userId).getRoles().split("[ ,;]"));
        Set<String> authorities = new LinkedHashSet<>(roles);
        authorities.add(ROLE_EMPLOYEE);
        if (admins.contains(login)) {
            authorities.add(ROLE_ADMIN);
        }
        return authorities;
    }
}
