package com.springboot.blogapp.springboot_blogapp_rest_api.service;

import com.springboot.blogapp.springboot_blogapp_rest_api.payload.LoginDTO;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.RegisterDTO;


public interface AuthService {
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
