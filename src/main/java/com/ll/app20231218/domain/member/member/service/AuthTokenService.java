package com.ll.app20231218.domain.member.member.service;

import com.ll.app20231218.domain.member.member.entity.Member;
import com.ll.app20231218.global.app.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    public String genToken(Member member, long expireSeconds) {
        Claims claims = Jwts
                .claims()
                .add("id", member.getId() + "")
                .add("username", member.getUsername())
                .add("authorities", member.getAuthoritiesAsStringList())
                .build();

        Date now = new Date();
        Date vaildity = new Date(now.getTime() + 1000 * expireSeconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(vaildity)
                .signWith(SignatureAlgorithm.HS256, AppConfig.getJwtSecretKey())
                .compact();
    }

    public String genAccessToken(Member member) {
        return genToken(member, 60 * 10);
    }

    public String genRefreshToken(Member member) {
        return genToken(member,60 * 60 * 24 * 365 * 1);
    }
}
