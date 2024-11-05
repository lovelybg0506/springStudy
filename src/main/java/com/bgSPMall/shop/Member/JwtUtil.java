package com.bgSPMall.shop.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
            ));

    // JWT 만들어주는 함수
    public static String createToken() {

        String jwt = Jwts.builder()
                .claim("username", "유저아이디")
                .claim("displayName", "유저이름")
                .issuedAt(new Date(System.currentTimeMillis())) // 언제 발행했는지
                .expiration(new Date(System.currentTimeMillis() + 10000)) //유효기간 10초(ms단위)
                .signWith(key)
                .compact();
        return jwt;
    }

    // JWT 까주는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims;
    }
}
