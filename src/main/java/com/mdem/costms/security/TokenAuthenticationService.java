package com.mdem.costms.security;

import com.mdem.costms.model.Role;
import com.mdem.costms.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TokenAuthenticationService {

    private static String TOKEN_PREFIX;
    private static long EXPIRATION_TIME;
    private static String SECRET;

    @Value("${security.tokenPrefix}")
    private void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }

    @Value("${security.expirationTime}")
    private void setExpirationTime(long expirationTime) {
        EXPIRATION_TIME = expirationTime;
    }

    @Value("${security.secret}")
    private void setSecret(String secret) {
        SECRET = secret;
    }

    public static String createToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", user.getId())
                .claim("roles", user.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    static String refreshToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", ((UserProxy)authentication.getDetails()).getId())
                .claim("roles", authentication.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().getSubject();
    }

    public static Long getUserIdFromToken(String token) {
        Number id = (Number) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().get("id");

        return id.longValue();
    }

    @SuppressWarnings("unchecked")
    static List<Role> getRolesFromToken(String token) {
        return (List) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().get("roles");
    }
}