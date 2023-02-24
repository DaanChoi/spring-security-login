package com.example.springsecuritylogin.service;

import com.example.springsecuritylogin.config.security.JwtTokenProvider;
import com.example.springsecuritylogin.domain.Entity.RefreshToken;
import com.example.springsecuritylogin.domain.Entity.User;
import com.example.springsecuritylogin.domain.dto.TokenInfo;
import com.example.springsecuritylogin.repository.LoginRepository;
import com.example.springsecuritylogin.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenInfo login(Map<String, String> user) {
        User userEntity = loginRepository.findByUniqueId(user.get("uniqueId"))
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(user.get("password"), userEntity.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(userEntity.getUniqueId(), userEntity.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(userEntity.getUniqueId(), userEntity.getRole());

        Long refreshTokenIndex = tokenRepository.save(RefreshToken.builder()
                .refreshToken(refreshToken)
                .user(loginRepository.findByUniqueId(userEntity.getUniqueId()).get())
                .build()).getId();

        return new TokenInfo(accessToken, refreshTokenIndex);
    }

    @Transactional
    public TokenInfo refresh(String refreshToken) {
        return new TokenInfo("", 1L);
    }
}
