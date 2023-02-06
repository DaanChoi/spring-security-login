package com.example.springsecuritylogin.config.security;

import com.example.springsecuritylogin.domain.Entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public SecurityUser(User user) {
        super(user.getId().toString(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getMember() {
        return user;
    }
}
