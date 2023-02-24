package com.example.springsecuritylogin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LoginResDto {
    private String grantType;
    private String accessToken;
    private Long refreshTokenIndex;
}
