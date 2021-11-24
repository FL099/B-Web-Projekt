package com.example.projekt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    static Algorithm algorithm = Algorithm.HMAC256("VeryVerySecureSecretKeyForJwtTokenHashAndStuff");

    public static String generateToken(String email){
        try {
            String token = JWT.create()
                    .withClaim("name", email)
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
            return jwt.toString();

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
        return "-1";
    }
}

