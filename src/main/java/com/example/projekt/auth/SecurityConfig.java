package com.example.projekt.auth;

import com.example.projekt.util.Role;
import com.example.projekt.auth.filters.CustomAuthenticationFilter;
import com.example.projekt.auth.filters.CustomAuthorizationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    //84.113.84.90

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // the UserDetailService and BCryptPasswordEncoder must be injected.
        // These are Beans and must be configured in the application
        auth.userDetailsService(auth.getDefaultUserDetailsService()).passwordEncoder(bCryptPasswordEncoder); //TODO: Ka ob das so funktioniert
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Spring Security already takes care of the path "login" in UsernamePasswordAuthenticationFilter.
        // In order to change the path this adaption is needed
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        // NOTE: the order of authorizeRequests DO matter
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/auth/**").permitAll(); // request to "login" are permitted without any validation
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/user").permitAll();
        //http.authorizeRequests().antMatchers("/**").permitAll(); //TODO: löschen(ist nur zu Testzwecken)
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/auctions", "/auctions/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/products").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user").hasAnyAuthority(String.valueOf(Role.ADMIN)); // validation of request e.g. for GET:/api/users must have the role ROLE_ADMIN
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/{id}").hasAnyAuthority(String.valueOf(Role.USER)); // validation of request e.g. for GET:/api/users/{id} must have the role ROLE_USER

        http.authorizeRequests().anyRequest().authenticated(); // every incoming request should be authenticated
        http.addFilter(customAuthenticationFilter);

        // the CustomAuthorizationFilter must come before the other filters because every request must be intercepted before any other filters
        // it is only for the UsernamePasswordAuthenticationFilter class
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
