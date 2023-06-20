package com.example.backend.security;

import com.example.backend.security.exceptions.UserIsNotConfirmedException;
import com.example.backend.security.models.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Optional;
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppUserService appUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(basic -> authenticationEntryPoint())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET, "/api/user/*").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/user/*").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/user/confirm/list").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/user/*").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/api/user/confirm/*").hasRole("ADMIN");
                    auth.requestMatchers("/api/todo/**").authenticated();
                    auth.requestMatchers("/api/user/logout").authenticated();
                    auth.anyRequest().permitAll();
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .logout(logout -> logout
                        .logoutUrl("api/user/logout")
                        .logoutSuccessHandler(((request, response, authentication) -> response.setStatus(HttpStatus.OK.value()))))
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
            GrantedAuthority authority = () -> appUser.role().toString();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(appUser, null, List.of(authority)));
            return User.builder().username(appUser.username()).password(appUser.password()).roles(appUser.role().toString()).build();
        };


    }
}