package com.vou.backend.utils;

import com.vou.backend.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.security.Keys;
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("username",user.getUsername());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(String.valueOf(user.getId()))
                    .setExpiration(new Date(System.currentTimeMillis()+expiration*1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        } catch (InvalidKeyException e) {
            //can inject logger, user logger instead of system out
            throw new InvalidKeyException(e.getMessage());
        }
    }
    private Key getSignInKey()
    {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public boolean isTokenExpired(String token)
    {
        Date expirationDate = this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractIdUser(String token)
    {
        Claims claims = extractAllClaims(token);
        return claims.get("email").toString();
    }
    public String extractEmail(String token)
    {
        Claims claims = extractAllClaims(token);
        return  claims.get("email",String.class);
    }
    public String extractUsername(String token)
    {
        Claims claims = extractAllClaims(token);
        return claims.get("username",String.class);
    }
    public boolean validateToken(String token, UserDetails userDetails)
    {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
}