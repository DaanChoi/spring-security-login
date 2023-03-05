package com.example.springsecuritylogin.security;

import com.example.springsecuritylogin.domain.entity.User;
import com.example.springsecuritylogin.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityUserDetailService implements UserDetailsService {

    private final LoginRepository loginRepository;

    @Autowired
    public SecurityUserDetailService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loginRepository.findByUniqueId(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username + " 사용자 없음"));
        return new SecurityUser(user); // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    }
}
