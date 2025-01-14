package com.hcmus.apigateway.util;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Extract the username from the given JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    public String getIdFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().get("id", String.class);
    }
    /**
     * Validate the given JWT token.
     *
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            // Parse the token and verify its signature
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true; // Token is valid
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage()); // Log invalid token error
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage()); // Log expired token error
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage()); // Log unsupported token error
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage()); // Log empty claims error
        }
        return false; // Token is invalid
    }
}