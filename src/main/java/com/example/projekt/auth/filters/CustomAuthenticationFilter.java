package com.example.projekt.auth.filters;

import com.example.projekt.data.model.Auth;
import com.example.projekt.util.JwtUtil;
import com.example.projekt.util.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Auth auth = new Auth();
        if (user.getAuthorities().contains("USER")){
            auth = new Auth(user.getUsername(), user.getPassword(), Role.USER);
        }else if (user.getAuthorities().contains("ADMIN")){
            auth = new Auth(user.getUsername(), user.getPassword(), Role.ADMIN);
        }

        String access_token = JwtUtil.generateAccessToken(request, auth);
        String refresh_token = JwtUtil.generateRefreshToken(request, auth);
        JwtUtil.setResponseBodyToken(response, access_token, refresh_token);


        //super.successfulAuthentication(request, response, chain, authResult);
    }
}
