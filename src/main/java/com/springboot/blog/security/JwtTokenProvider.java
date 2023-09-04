package com.springboot.blog.security;

import com.springboot.blog.dto.JwtAuthResponse;
import com.springboot.blog.exception.BlogApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;;
import java.util.Date;

/**
 * JWT Token Provider Service
 */
@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret-key}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    // generate JWT token
    public JwtAuthResponse generateToken(Authentication authentication) {

        // from Authentication, we will fetch the username or email of the user
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        // create jwt token
        final String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return JwtAuthResponse.builder()
                .accessToken(token)
                .expiresIn(jwtExpirationDate)
                .build();
    }

    // get the signature key
    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    //get username from JWT token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (MalformedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (SignatureException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid signature");
        } catch (IllegalArgumentException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        } catch (UnsupportedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }
    }
}
