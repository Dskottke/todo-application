package com.example.backend.security;

import com.example.backend.security.exceptions.UserIsNotConfirmedException;
import com.example.backend.security.models.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppUserService appUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/user/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/user/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/user/confirm/list").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/user/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/user/confirm/*").hasRole("ADMIN")
                .requestMatchers("/api/todo/**").authenticated()
                .requestMatchers("/api/user/logout").authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .logout().logoutUrl("/api/user/logout").logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpStatus.OK.value()))
                .and()
                .build();

    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint(new ObjectMapper());
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<AppUser> user = appUserService.findByUsername(username);

            if (user.isEmpty()) {
                throw new UsernameNotFoundException(username);
            }
            if (!user.get().confirmed()) {
                throw new UserIsNotConfirmedException("User is not confirmed");
            }

            AppUser appUser = user.get();

            return User.builder().username(appUser.username()).password(appUser.password()).roles(appUser.role().toString()).build();
        };


    }
}