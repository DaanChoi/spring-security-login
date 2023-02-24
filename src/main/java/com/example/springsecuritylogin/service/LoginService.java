package com.example.springsecuritylogin.service;

import com.example.springsecuritylogin.config.security.JwtTokenProvider;
import com.example.springsecuritylogin.domain.Entity.RefreshToken;
import com.example.springsecuritylogin.domain.dto.LoginResDto;
import com.example.springsecuritylogin.domain.dto.TokenInfo;
import com.example.springsecuritylogin.repository.LoginRepository;
import com.example.springsecuritylogin.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

//    public LoginService(LoginRepository loginRepository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
//        this.loginRepository = loginRepository;
//        this.authenticationManagerBuilder = authenticationManagerBuilder;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }


    @Transactional
    public LoginResDto login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password); // 인자 => principal(주요한) :  / credentials(자격) :

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        
        // 아직 인증되지 않은 Authentication 객체를 생성한 것(2)이고 추후 모든 인증이 완료되면 인증된 생성자로 Authentication 객체가 생성된다는 것(3)

        Long refreshTokenId = tokenRepository.save(RefreshToken.builder()
                .refreshToken(tokenInfo.getRefreshToken())
                .user(loginRepository.findByUniqueId(memberId).get())
                .build()).getId();
        return new LoginResDto(tokenInfo.getGrantType(), tokenInfo.getAccessToken(), refreshTokenId);
    }

}
