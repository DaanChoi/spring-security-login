package com.example.springsecuritylogin.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@RequiredArgsConstructor
public class PostLoginReq {
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String uniqueId;
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String nickname;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(uniqueId, password);
    }
}
