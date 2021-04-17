package ru.vsu.cs.carsharing.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        String msg = jwtTokenProvider.resolveToken(request) == null ? "Authentication Failed" : "Forbidden";
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
    }
}
