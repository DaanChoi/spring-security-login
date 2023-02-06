package com.example.springsecuritylogin.service;

import com.example.springsecuritylogin.domain.dto.GetUserRes;
import com.example.springsecuritylogin.repository.LoginRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


}
