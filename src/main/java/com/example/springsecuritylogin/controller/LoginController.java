package com.example.springsecuritylogin.controller;

import com.example.springsecuritylogin.config.BaseResponse;
import com.example.springsecuritylogin.config.security.JwtTokenProvider;
import com.example.springsecuritylogin.domain.Entity.User;
import com.example.springsecuritylogin.domain.Role;
import com.example.springsecuritylogin.domain.dto.GetUserRes;
import com.example.springsecuritylogin.repository.LoginRepository;
import com.example.springsecuritylogin.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.springsecuritylogin.config.BaseResponseStatus.*;

@RestController
@Slf4j
public class LoginController {

    final private LoginService loginService;
    final private LoginRepository loginRepository;
    final private JwtTokenProvider jwtTokenProvider;
    final private PasswordEncoder passwordEncoder;

    public LoginController(LoginService loginService, LoginRepository loginRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.loginRepository = loginRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public Long signup(@RequestBody Map<String, String> user) {
        return loginRepository.save(User.builder()
                .email(user.get("email"))
                .nickname(user.get("nickname"))
                .password(passwordEncoder.encode(user.get("password")))
                .role(Role.ROLE_USER)
                .build()
                ).getId();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        // 해당 이메일을 가진 회원이 있는지 확인합니다.
        User member = loginRepository.findByEmail(user.get("email"))
                        .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        // 입력받은 비번과 회원의 비번과 일치한지 확인합니다.
        if(!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 해당 이메일의 회원이 존재하고 비밀번호도 일치한다면, JWT 를 생성합니다.
        return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
    }

    /**
     * 회원 정보 조회
     */
    @GetMapping("/users")
    public BaseResponse<List<GetUserRes>> getUsers() {
        try {
            List<GetUserRes> users = loginRepository.findAll().stream()
                    .map(GetUserRes::new)
                    .collect(Collectors.toList());
            log.info(loginRepository.findAll().toString());
            return new BaseResponse<>(users, GET_SUCCESS);
        } catch (Exception e) {
            return new BaseResponse<>(INTERNAL_SERVER_ERROR);
        }
    }
}
