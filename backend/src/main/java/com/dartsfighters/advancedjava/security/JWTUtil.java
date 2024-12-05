package com.dartsfighters.advancedjava.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private String secret = "imasecretforeveryone";

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
            .withSubject(username) // Set the subject to the username
            .withIssuedAt(new Date())
            .withIssuer("darts-fighters")
            .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid or expired JWT token");
        }
    }

}