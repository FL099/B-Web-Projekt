package com.example.projekt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.projekt.data.model.Auth;

import java.util.Calendar;

public class JwtUtil {
    //https://github.com/auth0/java-jwt

    static Algorithm algorithm = Algorithm.HMAC256("VeryVerySecureSecretKeyForJwtTokenHashAndStuff");

    public static String generateToken(Auth auth){
        try {
            Calendar exp = Calendar.getInstance();
            exp.add(Calendar.HOUR_OF_DAY, 1);
            String token = JWT.create()
                    .withClaim("name", auth.getEmail())
                    .withExpiresAt(exp.getTime())
                    .withIssuer("DrinkMarket")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return "Something went wrong while processing the JWT generation";
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
}

