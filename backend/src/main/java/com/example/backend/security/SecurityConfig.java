package com.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppUserService appUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(r -> true)
                        .authenticated())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<AppUser> user = appUserService.findByUsername(username);

            if (user.isEmpty()) {
                throw new UsernameNotFoundException(username);
            }

            AppUser appUser = user.get();

            return User.builder().username(appUser.username()).password(appUser.password()).roles("USER").build();
        };


    }
}