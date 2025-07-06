package com.hotelmanagement.service;

import com.hotelmanagement.model.LoginRequest;
import com.hotelmanagement.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    JwtService jwtService;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    public String loginRequest(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if(authentication.isAuthenticated()) {
            return new LoginResponse((jwtService.generateToken(request.getUsername()))).getToken();
        }
        else return "Login failed";
    }
}
