package com.hotelmanagement.controller;

import com.hotelmanagement.model.LoginRequest;
import com.hotelmanagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/")
    public String login(@RequestBody LoginRequest request) {
        return loginService.loginRequest(request);
    }

}
