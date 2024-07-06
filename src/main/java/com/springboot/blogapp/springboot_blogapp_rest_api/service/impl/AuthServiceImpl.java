package com.springboot.blogapp.springboot_blogapp_rest_api.service.impl;

import com.springboot.blogapp.springboot_blogapp_rest_api.entity.Role;
import com.springboot.blogapp.springboot_blogapp_rest_api.entity.User;
import com.springboot.blogapp.springboot_blogapp_rest_api.exception.BlogAPIException;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.LoginDTO;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.RegisterDTO;
import com.springboot.blogapp.springboot_blogapp_rest_api.repository.RoleRepository;
import com.springboot.blogapp.springboot_blogapp_rest_api.repository.UserRepository;
import com.springboot.blogapp.springboot_blogapp_rest_api.security.JwtTokenProvider;
import com.springboot.blogapp.springboot_blogapp_rest_api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {


    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider
                           ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),loginDTO.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
//        return "User Logged in successfully!!";
        return token;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        // add check for username exists in database
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username already Exists!!");
        }

        //add check for email exits in database
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email already Exists!!");
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User Registered Successfully!";
    }
}
