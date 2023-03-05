package com.example.springsecuritylogin.domain.dto;

import com.example.springsecuritylogin.domain.entity.User;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserRes {

    private String uniqueId;
    private String password;

    @Builder
    public GetUserRes(User user){
        this.uniqueId = user.getUniqueId();
        this.password = user.getPassword();
    }
}
