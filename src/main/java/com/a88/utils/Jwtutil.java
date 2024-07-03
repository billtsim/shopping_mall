package com.a88.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class Jwtutil {

    private static String secretKey = "kdfjkjkdf342";

    private static Long expire = 50000000L;


    public static String generateJwt(Map<String, Object> claims) {

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expire))// set 有效時長
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return jwt;
    }


    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();

        return claims;
    }
}
