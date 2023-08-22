package com.bubblebubble.hr.login.config;


import com.bubblebubble.hr.login.jwt.JwtFilter;
import com.bubblebubble.hr.login.jwt.TokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private final TokenProvider tokenProvider;


    public JwtSecurityConfig(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtFilter customeFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(customeFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

