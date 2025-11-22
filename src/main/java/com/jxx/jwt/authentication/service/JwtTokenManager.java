package com.jxx.jwt.authentication.service;

import com.jxx.jwt.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenManager {

    @Value("${authentication.jwt.issuer}")
    private String issuer;
    @Value("${authentication.jwt.secret-key}")
    private String secretKey;

    public String doProvide(Member member) {
        SecretKey sign = new SecretKeySpec(secretKey.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());

        String jwtToken = Jwts.builder()
                .issuer(issuer)
                .issuedAt(new Date())
                .signWith(sign)
                .claim("memberName", member.getMemberName())
                .claim("employeeId", member.getEmployeeId())
                .claim("roleLevel", member.getRoleLevel())
                .compact();

        return jwtToken;
    }

    public Boolean doValidate(String jwtToken) {
        SecretKey sign = new SecretKeySpec(secretKey.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());

        JwtParser jwtParser = Jwts.parser()
                .decryptWith(sign)
                .build();

        boolean signed = jwtParser.isSigned(jwtToken);
        return signed;
    }
}



















