package com.hotelmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    AuthEntryPoint authEntryPoint;

    String [] WHITE_LIST_URL = {"/login/", "/user/addUser"};
    String [] ADMIN_WHITE_LIST_URL = {"/user/addAdmin", "/user/deleteAdmin/**", "/rooms/delete/**"};
    String [] MGMT_WHITE_LIST_URL = {"/user/addStaff", "/user/deleteStaff",
                                        "/booking/viewAll", "/booking/roomId/**", "/booking/userId/**",
                                        "/rooms/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(ADMIN_WHITE_LIST_URL).hasAuthority("ADMIN")
                                .requestMatchers(MGMT_WHITE_LIST_URL).hasAnyAuthority("ADMIN", "STAFF")
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
