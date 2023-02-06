package com.example.springsecuritylogin.config;

import com.example.springsecuritylogin.repository.LoginRepository;
import com.example.springsecuritylogin.service.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginConfig {

    private final LoginRepository loginRepository;

    public LoginConfig(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Bean
    public LoginService loginService() {
        return new LoginService(loginRepository);
    }


}
