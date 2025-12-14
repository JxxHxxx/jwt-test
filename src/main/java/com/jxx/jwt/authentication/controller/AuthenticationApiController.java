package com.jxx.jwt.authentication.controller;

import com.jxx.jwt.authentication.dto.LoginRequest;
import com.jxx.jwt.authentication.dto.LoginResponse;
import com.jxx.jwt.authentication.service.JwtTokenManager;
import com.jxx.jwt.member.domain.Member;
import com.jxx.jwt.member.repository.MemberRepository;
import io.jsonwebtoken.JwtBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationApiController {

    @Value("${http.cookie.http-only}")
    private boolean httpOnly;

    private final JwtTokenManager jwtTokenManager;

    @PostMapping("/v1/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        if (!Objects.equals(loginRequest.employeeId(), loginRequest.password())) {
            throw new IllegalStateException("아이디/비밀번호가 올바르지 않습니다.");
        }

        Member member = Member.builder()
                .employeeId(loginRequest.employeeId())
                .memberName("jxxhxxx")
                .roleLevel("R")
                .build();

        String authenticateToken = jwtTokenManager.doProvide(member);

        ResponseCookie cookie = ResponseCookie.from("token", authenticateToken)
                .httpOnly(httpOnly)
                .path("/")
                .build();

        log.info("response success");
        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body("Success Login");
    }

    @GetMapping("/v1/api/reload")
    public ResponseEntity<?> reload() {
        return ResponseEntity.ok().body("reload");
    }

    @GetMapping("/v1/api/cors-header-check")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok()
                .header("jxx-token", UUID.randomUUID().toString())
                .header("jxx-authorization", UUID.randomUUID().toString()) // 클라이언트에 노출 허용 X
                .body("hello");
    }
}
