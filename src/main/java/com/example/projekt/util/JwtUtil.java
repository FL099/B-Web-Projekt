package com.example.projekt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.projekt.data.model.Auth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil {
    //https://github.com/auth0/java-jwt

    static Algorithm algorithm = Algorithm.HMAC256("VeryVerySecureSecretKeyForJwtTokenHashAndStuff");

    public static String generateToken(Auth auth){
        try {
            Calendar exp = Calendar.getInstance();
            exp.add(Calendar.HOUR_OF_DAY, 1);
            String token = JWT.create()
                    .withSubject(auth.getEmail())
                    .withClaim("name", auth.getEmail())
                    .withClaim("roles", auth.getRole().toString())
                    .withExpiresAt(exp.getTime())
                    .withIssuer("DrinkMarket")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return "Something went wrong while processing the JWT generation";
    }

    public static String generateAccessToken(HttpServletRequest request, Auth auth) {
        return JWT.create().withSubject(auth.getEmail()) // e.g.: userId, username, etc..
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 Minutes
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", auth.getRole().toString()) // all the claims of the user will be passed to the token
                .sign(algorithm);
    }

    public static String generateRefreshToken(HttpServletRequest request, Auth auth) {
        return JWT.create().withSubject(auth.getEmail()) // e.g.: userId, username, etc..
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 Minutes
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public static void setResponseBodyToken(HttpServletResponse response, String access_token, String refresh_token) {
        // In this variant the response body is empty, but the header contains the token
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);

        // In this variant the response body contains the token, but the header does not include token
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (IOException ex) {
            new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static String verifyToken(String token){
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("DrinkMarket")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            String tmp = "";
            tmp += jwt.getExpiresAt() + "\n";
            tmp += jwt.getClaim("name") + "\n";
            tmp += jwt.getIssuer();
            return tmp;

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
        return "Authentifizierung fehlgeschlagen";
    }

    public static DecodedJWT getDecodedJWT(String token) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }
}

