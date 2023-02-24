package com.example.springsecuritylogin.controller;

import com.example.springsecuritylogin.config.BaseResponse;
import com.example.springsecuritylogin.config.security.JwtTokenProvider;
import com.example.springsecuritylogin.domain.Entity.RefreshToken;
import com.example.springsecuritylogin.domain.Entity.User;
import com.example.springsecuritylogin.domain.Role;
import com.example.springsecuritylogin.domain.dto.GetUserRes;
import com.example.springsecuritylogin.domain.dto.LoginResDto;
import com.example.springsecuritylogin.domain.dto.TokenInfo;
import com.example.springsecuritylogin.repository.LoginRepository;
import com.example.springsecuritylogin.repository.TokenRepository;
import com.example.springsecuritylogin.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.springsecuritylogin.config.BaseResponseStatus.*;

@RestController
@Slf4j
public class LoginController {

    final private LoginService loginService;
    final private LoginRepository loginRepository;
    final private TokenRepository tokenRepository;
    final private JwtTokenProvider jwtTokenProvider;
    final private PasswordEncoder passwordEncoder;

    public LoginController(LoginService loginService, LoginRepository loginRepository,
                           TokenRepository tokenRepository,
                           JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.loginRepository = loginRepository;
        this.tokenRepository = tokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public Long signup(@RequestBody Map<String, String> user) {
        User userEntity = loginRepository.save(User.builder()
                .uniqueId(user.get("uniqueId"))
                .nickname(user.get("nickname"))
                .password(passwordEncoder.encode(user.get("password")))
                .role(Role.ROLE_USER)
                .build());

        return userEntity.getId();

    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        TokenInfo tokenInfo = loginService.login(user);
        return ResponseEntity.ok(tokenInfo);
    }

    /**
     * Access 토큰 재발급
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7);
        }
        return ResponseEntity.ok("");
    }

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
