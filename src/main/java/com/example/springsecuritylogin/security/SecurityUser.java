package com.example.springsecuritylogin.security;

import com.example.springsecuritylogin.domain.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public SecurityUser(User user) {
        super(user.getId().toString(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().toString()));
        this.user = user;
    }

    public User getMember() {
        return user;
    }
}
