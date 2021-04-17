package ru.vsu.cs.carsharing.security;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.vsu.cs.carsharing.exception.WebException;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    public static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Keychain.JWT_KEY));
    private final Keychain keychain;

    public String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isBearer(String token) {
        return token != null && token.startsWith(BEARER);
    }

    public String extractBearer(String token) {
        return token.substring(BEARER.length());
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(AUTHORIZATION_HEADER);
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException ex) {
            throw new WebException("Invalid token", ex, HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getUserAuthentication(String token) {
        String subject = getUsernameFromToken(token);
        String login = TokenUtils.getLogin(subject);
        int userId = TokenUtils.getUserId(subject);
        Set<GrantedAuthority> authorities = keychain.getAuthorities(login, userId);
        UserDetails userDetails = User.withUsername(subject)
                .password("")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .authorities(authorities)
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
