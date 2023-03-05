package com.example.springsecuritylogin.controller;

import com.example.springsecuritylogin.jwt.JwtTokenProvider;
import com.example.springsecuritylogin.domain.dto.GetUserRes;
import com.example.springsecuritylogin.domain.dto.TokenInfo;
import com.example.springsecuritylogin.repository.LoginRepository;
import com.example.springsecuritylogin.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    final private LoginService loginService;
    final private LoginRepository loginRepository;
    final private JwtTokenProvider jwtTokenProvider;
    final private PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */

    /**
     * 로그인
     */
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
//        TokenInfo tokenInfo = loginService.login(user);
//        return ResponseEntity.ok(tokenInfo);
//    }

    /**
     * 회원 정보 조회
     */
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<GetUserRes> users = loginRepository.findAll().stream()
                                                .map(GetUserRes::new) // 스트림 내 요소들을 하나씩 특정 값으로 변환
                                                .collect(Collectors.toList()); // 스트림 객체를 리스트로 변환 ??
        log.info(loginRepository.findAll().toString());
        return ResponseEntity.ok(users);
    }
}
