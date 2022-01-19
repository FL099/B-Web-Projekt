package com.example.projekt.auth.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.projekt.util.JwtUtil;
import com.example.projekt.util.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/auth") || request.getServletPath().equals("/auth/check")){
        }else{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = new String[]{"USER","GUEST"};

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Arrays.stream(roles).forEach( role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });

                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,null, authorities));
                }catch (Exception e) {
                    //log.error("Error logging in: {}", e.getMessage());
//                    response.sendError(HttpStatus.FORBIDDEN.value()); // this returns only the response status, but no error message in the response body
                    //jwtUtility.setResponseBodyException(response, e);
                    new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }

        }

        filterChain.doFilter(request, response);
    }
}
