package com.a88;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class webManagementApplicationTests {

    // generate JWT
    @Test
    public void testGenJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("name", "bill");

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))// set 有效時長
                .signWith(SignatureAlgorithm.HS256, "kkdfjd2328jdjdljfo")
                .compact();

        System.out.println(jwt);


    }

    @Test
    // 過了有效時間校驗JWT會報錯
    public void testParseJwt() {
        Claims claims = Jwts.parser()
                .setSigningKey("kkdfjd2328jdjdljfo")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYmlsbCIsImlkIjoxLCJleHAiOjE3MTU3NjcxOTJ9.6F1MUqZaHo91lrDiqkafM7nP_hPv_DEsSl2WD53dlr8")
                .getBody();

        System.out.println(claims);
    }
}
