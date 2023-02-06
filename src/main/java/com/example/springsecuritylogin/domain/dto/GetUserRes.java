package com.example.springsecuritylogin.domain.dto;

import com.example.springsecuritylogin.domain.Entity.User;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserRes {
    private Long userIdx;
    private String email;
    private String nickname;

    @Builder
    public GetUserRes(User user){
        this.userIdx = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
