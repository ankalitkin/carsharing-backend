package ru.vsu.cs.carsharing.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ru.vsu.cs.carsharing.exception.WebException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.debug("Entering token filter");
        if (!(request instanceof HttpServletRequest)) {
            throw new WebException("Unsupported request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Authentication auth = null;
        if (jwtTokenProvider.isBearer(token)) {
            String bearerToken = jwtTokenProvider.extractBearer(token);
            if (jwtTokenProvider.validateToken(bearerToken)) {
                auth = jwtTokenProvider.getUserAuthentication(bearerToken);
            }
        }
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
