package com.elearn.app.start_learn_back.config.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.hibernate.annotations.DialectOverride;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Component
public class JwtUtil {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private long jwtExpiration = 5 * 60 * 1000; // 5 mins

    // extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // the below code is the implementation of the above code.
    // We are actually extracting the required information from the token in the form of claims.
    //
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken(String userName) {
        return createToken(userName);
    }

    private String createToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour
                .signWith(key)
                .compact();
    }
    // .compact();
    // The JWT builder finalizes the token by:
    //Encoding the Header & Payload in Base64Url format.
    //Generating a Signature using the provided key.
    //Combining all three parts into a single string.


    //If you want to decode or verify a JWT, you must parse it back using Jwts.parser().
    public Boolean validateToken(String token, String userName){
        String tokenUsername = extractUsername(token);
        return (userName.equals(tokenUsername) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}