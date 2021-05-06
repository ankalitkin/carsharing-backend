package ru.vsu.cs.carsharing.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.vsu.cs.carsharing.exception.WebException;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    public static final String BEARER = "Bearer ";
    public static final long TTL_MILLIS = 7 * 24 * 3600 * 1000;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Keychain.JWT_KEY));
    private final Keychain keychain;

    public String createToken(String subject, String audience) {
        return Jwts.builder()
                .setSubject(subject)
                .setAudience(audience)
                .setExpiration(new Date(System.currentTimeMillis() + TTL_MILLIS))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return claims.getExpiration().after(new Date());
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

    public UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String subject = body.getSubject();
            return new UserDetails(TokenUtils.getUserId(subject), TokenUtils.getLogin(subject), body.getAudience());
        } catch (JwtException ex) {
            throw new WebException("Invalid token", ex, HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getUserAuthentication(String token) {
        UserDetails details = getUserDetailsFromToken(token);
        Set<GrantedAuthority> authorities = keychain.getAuthorities(details);
        return new UsernamePasswordAuthenticationToken(details, "", authorities);
    }

}
